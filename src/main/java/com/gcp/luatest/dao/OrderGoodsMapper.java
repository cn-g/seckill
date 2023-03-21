package com.gcp.luatest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcp.luatest.entity.OrderGoods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {
}
