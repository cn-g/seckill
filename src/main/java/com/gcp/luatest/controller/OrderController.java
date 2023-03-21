package com.gcp.luatest.controller;

import com.gcp.luatest.config.RedisCache;
import com.gcp.luatest.dto.SecKillReqDto;
import com.gcp.luatest.response.CommonException;
import com.gcp.luatest.response.ResponseEntity;
import com.gcp.luatest.response.Result;
import com.gcp.luatest.service.OrderService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String ORDER_KEY = "cache_order:";

    /**
     * 处理完成的订单
     */
    private static final String ORDER_DEAL_KEY = "cache_order_deal:";

    @Resource
    OrderService orderService;

    @Resource
    RedisCache redisCache;

    /**
     * 秒杀请求
     * @param secKillReqDto
     * @return
     */
    @PostMapping("/secKill")
    public Result secKill(@RequestBody SecKillReqDto secKillReqDto){
        String key = ORDER_KEY+secKillReqDto.getUserId();
        boolean result = false;
        Long lockResult = 0L;
        try{
            //加锁
            lockResult = (Long) redisCache.lock(key,5);
            if(lockResult==1L){
                result = orderService.secKill(secKillReqDto);
            }
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }finally {
            if(lockResult==1L){
                //加锁成功，进行解锁
                redisCache.unlock(key);
            }
        }
        if(result){
            return ResponseEntity.ok();
        }else{
            throw new CommonException("秒杀请求失败");
        }
    }

    /**
     * 检验订单是否处理完成
     * @param userId
     * @return
     */
    @GetMapping("/checkOrderByUserId")
    public Result<Boolean> checkOrderByUserId(Integer userId,String goodsId){
        Integer result = redisCache.getCacheObject(ORDER_DEAL_KEY+userId+":"+goodsId);
        return ResponseEntity.ok(result!=null);
    }

}
