package com.gcp.luatest.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoodsServiceTest {

    @Resource
    private GoodsService goodsService;

    @Test
    void initGoods() {
        goodsService.initGoods();
    }

    @Test
    void initRedis() {
        goodsService.initRedis();
    }

    @Test
    void sysNum() {
        for (int i = 0; i < 200; i++) {
            System.out.println(1000+i);
        }
    }
}