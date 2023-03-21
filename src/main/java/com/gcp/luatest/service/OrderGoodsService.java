package com.gcp.luatest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcp.luatest.dao.OrderGoodsMapper;
import com.gcp.luatest.entity.OrderGoods;
import org.springframework.stereotype.Service;

@Service
public class OrderGoodsService extends ServiceImpl<OrderGoodsMapper, OrderGoods> {
}
