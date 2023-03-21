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
@TableName("m_order")
public class Order {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("order_price")
    private BigDecimal orderPrice;

    @TableField("user_id")
    private Integer userId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("status")
    private Integer status;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 过期时间
     */
    @TableField("expiration_time")
    private LocalDateTime expiration_time;

}
