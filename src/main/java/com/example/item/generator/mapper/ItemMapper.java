package com.example.item.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.item.generator.domain.Item;
import org.apache.ibatis.annotations.Param;


public interface ItemMapper extends BaseMapper<Item> {


    Integer minusCount(@Param("id") String id, @Param("count") Integer count);
}
