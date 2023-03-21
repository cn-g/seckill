package com.gcp.luatest.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcp.luatest.config.RedisCache;
import com.gcp.luatest.dao.OrderMapper;
import com.gcp.luatest.dto.SecKillReqDto;
import com.gcp.luatest.entity.Goods;
import com.gcp.luatest.entity.Order;
import com.gcp.luatest.entity.OrderGoods;
import com.gcp.luatest.response.CommonException;
import com.gcp.luatest.tools.ReflectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单服务类
 * @author gcp
 */
@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    /**
     * 等待处理订单
     */
    private static BlockingQueue<HashMap<String,Object>> orders = new ArrayBlockingQueue<>(1024);

    /**
     * 处理失败订单
     */
    private static BlockingQueue<HashMap<String,Object>> loseOrders = new ArrayBlockingQueue<>(1024);

    /**
     * 处理完成的订单
     */
    private static final String ORDER_DEAL_KEY = "cache_order_deal:";

    /**
     * 定义线程池
     */
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(2,4, 30,TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),new TestThreadFactory());

    /**
     * 自定义线程工厂
     */
    private static class TestThreadFactory implements ThreadFactory{

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("线程"+count.addAndGet(1));
            return t;
        }
    }

    @Resource
    RedisCache redisCache;

    @Resource
    GoodsService goodsService;
    @Resource
    OrderGoodsService orderGoodsService;

    /**
     * spring容器初始化的时候执行
     */
    @PostConstruct
    private void orderhandle() {
        //处理等待队列
        POOL_EXECUTOR.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                    while(true) {
                        //队列中取出商品id
                        HashMap<String,Object> take = orders.take();
                        if(!take.isEmpty()) {
                            //通过反射获取OrderService类
                            OrderService orderService = ReflectUtil.getBean(OrderService.class);
                            try {
                                orderService.createOrder(take);
                            } catch (Exception e) {
                                e.printStackTrace();
                                // 处理失败的订单进入失败订单队列
                                loseOrders.put(take);
                            }
                        }
                    }
            }
        });
        //处理失败队列
        POOL_EXECUTOR.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                while(true) {
                    //队列中取出商品id
                    HashMap<String,Object> take = loseOrders.take();
                    if(!take.isEmpty()) {
                        log.info("秒杀请求失败,请重新下单:{}",take.get("orderId"));
                    }
                }
            }
        });
    }

    /**
     * 创单逻辑
     * @param take
     */
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(HashMap<String,Object> take){
            log.info("开始处理订单:{}",take.get("orderId"));
            SecKillReqDto secKillReqDto = JSON.parseObject(take.get("orderId").toString(),SecKillReqDto.class);
            Goods goods = goodsService.getById(secKillReqDto.getGoodsId());
            //更新商品库存
            goodsService.update(Wrappers.lambdaUpdate(Goods.class).set(Goods::getStockNum,goods.getStockNum()-secKillReqDto.getNum()).set(Goods::getFreezeNum,goods.getFreezeNum()+secKillReqDto.getNum()).set(Goods::getSaleNum,goods.getSaleNum()+secKillReqDto.getNum()).eq(Goods::getId,secKillReqDto.getGoodsId()));
            //订单信息
            Order order = new Order().setOrderPrice(goods.getPrice()).setCreateTime(LocalDateTime.now()).setUserId(secKillReqDto.getUserId()).setStatus(0).setExpiration_time(LocalDateTime.now().plusMinutes(10));
            this.save(order);
            //订单商品信息
            OrderGoods orderGoods = new OrderGoods().setUserId(secKillReqDto.getUserId())
                    .setGoodsId(secKillReqDto.getGoodsId())
                    .setNum(secKillReqDto.getNum())
                    .setCreateTime(LocalDateTime.now())
                    .setPrice(goods.getPrice().multiply(BigDecimal.valueOf(secKillReqDto.getNum())))
                    .setOrderId(order.getId()).setStatus(0);
            orderGoodsService.save(orderGoods);
            //保存处理完成信息,保存10s
            redisCache.setCacheObject(ORDER_DEAL_KEY+secKillReqDto.getUserId()+":"+secKillReqDto.getGoodsId(),1,10,TimeUnit.SECONDS);
            log.info("处理订单:{}结束",order.getId());
    }

    /**
     * 秒杀
     * @param secKillReqDto 秒杀入参
     */
    public Boolean secKill(SecKillReqDto secKillReqDto) throws InterruptedException {
        List<String> list = new ArrayList<>();
        Object executeRes;
        try {
            //运行lua脚本
            executeRes = redisCache.runRedisScript("secKill.lua",list , secKillReqDto.getGoodsId(),secKillReqDto.getUserId(),secKillReqDto.getNum());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("秒杀请求失败！！！！");
        }
        if ((Long) executeRes == 1L) {
            // 将用户下单信息保存到阻塞队列
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderId", JSON.toJSONString(secKillReqDto));
            orders.put(hashMap);
            log.info("用户[{}]秒杀请求成功",secKillReqDto.getUserId());
            return true;
        } else if((Long) executeRes == -1L){
            throw new CommonException("库存不足");
        }else{
            throw new CommonException("限制购买");
        }
    }

}
