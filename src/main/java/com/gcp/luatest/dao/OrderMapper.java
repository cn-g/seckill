package com.gcp.luatest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcp.luatest.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
