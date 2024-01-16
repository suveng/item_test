package com.example.item.api;

import com.example.item.generator.domain.Item;
import com.example.item.generator.service.ItemService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/")
@EnableAsync
public class Api {

    @Resource
    private ItemService itemService;
    @Resource
    private RedisTemplate<String,Integer> redisTemplate;

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
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.multi();
                Integer count = redisTemplate.opsForValue().get(id);
                if (Objects.isNull(count)){
                    return null;
                }
                if (count > 0 && count > 1){
                    redisTemplate.opsForValue().decrement(id);
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
