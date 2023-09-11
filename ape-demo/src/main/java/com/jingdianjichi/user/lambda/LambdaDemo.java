package com.jingdianjichi.user.lambda;

import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: ChickenWing
 * @Description: 提供一些常见的lambda用法demo
 * @DateTime: 2022/12/10 22:01
 */
public class LambdaDemo {

    /**
     * 将一个list实体的某些属性转换为另一个list实体
     */
    public void streamMap() {
        List<SkuInfo> skuInfoList = new LinkedList<>();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSkuId(1L);
        skuInfo.setSkuName("商品1");
        skuInfo.setPrice(new BigDecimal("0.01"));
        skuInfoList.stream().map(sku -> {
            SkuVO skuVO = new SkuVO();
            skuVO.setSkuId(sku.getSkuId());
            skuVO.setSkuName(sku.getSkuName());
            return skuVO;
        });
    }

    /**
     * 将list转为map的demo
     */
    public void listToMap() {
        List<SkuInfo> skuInfoList = new LinkedList<>();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSkuId(1L);
        skuInfo.setSkuName("商品1");
        skuInfo.setPrice(new BigDecimal("0.01"));
        // 转为id和实体的map
        Map<Long, SkuInfo> resultMap = skuInfoList.stream()
                .collect(Collectors.toMap(SkuInfo::getSkuId, Function.identity(), (key1, key2) -> key2));

        //转为属性的map
        Map<Long, String> resultMap1 = skuInfoList.stream()
                .collect(Collectors.toMap(SkuInfo::getSkuId, SkuInfo::getSkuName, (key1, key2) -> key2));

        //分组map
        Map<Long, List<SkuInfo>> resultMap2 = skuInfoList.stream().collect(Collectors.groupingBy(SkuInfo::getSkuId));
    }

    @Data
    class SkuInfo {
        private Long skuId;
        private String skuName;
        private BigDecimal price;
    }

    @Data
    class SkuVO {
        private Long skuId;
        private String skuName;
    }

}
