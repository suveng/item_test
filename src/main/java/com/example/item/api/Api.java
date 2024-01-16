package com.example.item.api;

import com.example.item.generator.domain.Item;
import com.example.item.generator.service.ItemService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/")
@EnableAsync
public class Api {

    private static volatile  ConcurrentHashMap<String, AtomicInteger> MAP = new ConcurrentHashMap<>();
    @Resource
    private ItemService itemService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/items")
    public List<Item> list() {
        return itemService.list();
    }

    @PostMapping("/minus/{id}")
    public Integer minusCount(@PathVariable String id){
        return itemService.minusCount(id,1);
    }

    @PostMapping("/2/minus/{id}")
    public void minusCount2(@PathVariable String id){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("minus.lua")));
        redisTemplate.execute(redisScript, Collections.singletonList(id));
        itemService.minusCountAsync(id,1);
    }

    @PostMapping("/3/minus/{id}")
    public void minusCount3(@PathVariable String id){
        AtomicInteger atomicInteger = MAP.get(id);
        atomicInteger.decrementAndGet();
        itemService.minusCountAsync(id,1);
    }


    @PostMapping("/add/{id}")
    public void add(@PathVariable String id) {
        Item entity = new Item();
        entity.setId(Long.valueOf(id));
        entity.setRemark(id);
        entity.setCount(Integer.MAX_VALUE);
        itemService.save(entity);

//        redisTemplate.opsForValue().set(id, Integer.MAX_VALUE);
        MAP.put(id,new AtomicInteger(Integer.MAX_VALUE));
    }
}
