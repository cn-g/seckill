package com.gcp.luatest.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcp.luatest.config.RedisCache;
import com.gcp.luatest.dao.GoodsMapper;
import com.gcp.luatest.entity.Goods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {

    private static final String CACHESECKILLGOODS = "cache_seckill_goods:";

    @Resource
    private RedisCache redisCache;

    /**
     * 初始化商品信息
     */
    public void initGoods(){
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            goodsList.add(new Goods().setName("秒杀商品"+i).setPrice(BigDecimal.valueOf(i)).setStockNum(100).setFreezeNum(0).setSaleNum(0).setStatus(1));
        }
        this.saveBatch(goodsList);
    }

    /**
     * 初始化redis商品信息
     */
    public void initRedis(){
        List<Goods> list = this.list(Wrappers.lambdaQuery(Goods.class).eq(Goods::getStatus,1));
        list.forEach(l->{
            redisCache.setCacheObject(CACHESECKILLGOODS+l.getId(),l.getStockNum());
        });
    }

}
