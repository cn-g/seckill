package com.gcp.luatest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@TableName("m_goods")
public class Goods {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("price")
    private BigDecimal price;

    @TableField("stock_num")
    private Integer stockNum;

    @TableField("freeze_num")
    private Integer freezeNum;

    @TableField("sale_num")
    private Integer saleNum;

    @TableField("status")
    private Integer status;

}
