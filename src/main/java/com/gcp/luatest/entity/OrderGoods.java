package com.gcp.luatest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("m_order_goods")
public class OrderGoods {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("order_id")
    private Long orderId;

    @TableField("num")
    private Integer num;

    @TableField("price")
    private BigDecimal price;

    @TableField("user_id")
    private Integer userId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("status")
    private Integer status;

}
