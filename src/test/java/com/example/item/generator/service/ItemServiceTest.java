package com.example.item.generator.service;
import java.util.Date;

import com.example.item.ItemApplicationTests;
import com.example.item.generator.domain.Item;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;

public class ItemServiceTest extends ItemApplicationTests {
    @Resource
    private ItemService itemService;

    @Test
    void init() {
        ArrayList<Item> entityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Item e = new Item();
            e.setRemark(String.valueOf(i));
            entityList.add(e);
        }
        itemService.saveBatch(entityList);
    }
}