package com.example.item.api;

import com.example.item.generator.domain.Item;
import com.example.item.generator.service.ItemService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/")
@EnableAsync
public class Api {

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
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                V v = operations.opsForValue().get(id);
                if (v instanceof Integer) {
                    Integer count = (Integer) v;
                    if (count > 0 && count >= 1) {
                        operations.opsForValue().decrement((K) id);
                    }
                }
                return null;
            }
        });
        itemService.minusCountAsync(id,1);
    }

    @PostMapping("/add/{id}")
    public void add(@PathVariable String id) {
        Item entity = new Item();
        entity.setId(Long.valueOf(id));
        entity.setRemark(id);
        entity.setCount(Integer.MAX_VALUE);
        itemService.save(entity);

        redisTemplate.opsForValue().set(id, Integer.MAX_VALUE);
    }
}
