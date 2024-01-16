package com.example.item.generator.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName(value ="item")
@Data
public class Item implements Serializable {
    /**
     * 自增主键
     */
    @TableId
    private Long id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 库存
     */
    private Integer count;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 订单创建时间
     */
    private Date createdAt;
}