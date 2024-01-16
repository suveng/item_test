package com.example.item.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.item.generator.domain.Item;

public interface ItemService extends IService<Item> {

    /**
     * 扣减库存
     * @param id 库存id
     * @return 剩余库存
     */
    Integer minusCount(String id,Integer count);

    void minusCountAsync(String id,Integer count);

}
