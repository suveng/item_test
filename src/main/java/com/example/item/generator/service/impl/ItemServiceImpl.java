package com.example.item.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.item.generator.domain.Item;
import com.example.item.generator.mapper.ItemMapper;
import com.example.item.generator.service.ItemService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Override
    public Integer minusCount(String id, Integer count) {
        return getBaseMapper().minusCount(id, count);
    }

    @Override
    @Async
    public void minusCountAsync(String id, Integer count) {
        getBaseMapper().minusCount(id, count);
    }
}
