package com.jingdianjichi.redis.util;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @Author: ChickenWing
 * @Description: guava的cacheutil
 * @DateTime: 2022/11/27 16:45
 */
@Component
@Slf4j
public class GuavaCacheUtil<K, V> {

    @Resource
    public RedisUtil redisUtil;

    @Value("${guava.cache.switch}")
    public Boolean switchCache;

    private Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(3, TimeUnit.SECONDS)
                    .build();

    public Map<K, V> getResult(List<K> idList, String cacheKeyPrefix, String cacheSuffix, Class<V> clazz,
                               Function<List<K>, Map<K, V>> function) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyMap();
        }
        Map<K, V> resultMap = new HashMap<>(16);
        if (!switchCache) {
            resultMap = function.apply(idList);
            return resultMap;
        }
        List<K> noCacheIdList = new LinkedList<>();
        for (K id : idList) {
            String cacheKey = cacheKeyPrefix + "_" + id + "_" + cacheSuffix;
            String content = localCache.getIfPresent(cacheKey);
            if (StringUtils.isNotBlank(content)) {
                V v = JSON.parseObject(content, clazz);
                resultMap.put(id, v);
            } else {
                noCacheIdList.add(id);
            }
        }
        if (CollectionUtils.isEmpty(noCacheIdList)) {
            return resultMap;
        }
        Map<K, V> noCacheResultMap = function.apply(noCacheIdList);
        if (noCacheResultMap == null || noCacheResultMap.isEmpty()) {
            return resultMap;
        }
        for (Map.Entry<K, V> entry : noCacheResultMap.entrySet()) {
            K id = entry.getKey();
            V result = entry.getValue();
            resultMap.put(id, result);
            String cacheKey = cacheKeyPrefix + "_" + id + "_" + cacheSuffix;
            localCache.put(cacheKey, JSON.toJSONString(result));
        }
        return resultMap;
    }


}
