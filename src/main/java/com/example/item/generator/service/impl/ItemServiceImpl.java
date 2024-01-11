package com.example.item.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.item.generator.domain.Item;
import com.example.item.generator.mapper.ItemMapper;
import com.example.item.generator.service.ItemService;
import org.springframework.stereotype.Service;


@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
