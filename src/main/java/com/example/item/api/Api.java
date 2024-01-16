package com.example.item.api;

import com.example.item.generator.domain.Item;
import com.example.item.generator.service.ItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/")
public class Api {

    @Resource
    private ItemService itemService;

    @PostMapping("/items")
    public List<Item> list() {
        return itemService.list();
    }

    @PostMapping("/minus/{{id}}")
    public Integer minusCount(@PathVariable String id){
        return itemService.minusCount(id,-1);
    }
}
