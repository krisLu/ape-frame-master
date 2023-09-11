package com.jingdianjichi.user.init;

import com.alibaba.fastjson.JSON;
import com.jingdianjichi.user.designPattern.builderPattern.demo.SkuDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class ApplicationInit implements ApplicationListener<ApplicationReadyEvent> {

    Map<String, InitFunction> initFunctionMap = new HashMap<>();

    {
        initFunctionMap.put("预热fastjson", this::initFastJson);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initFunctionMap.forEach((desc, function) -> {
            try {
                long start = System.currentTimeMillis();
                function.invoke();
                log.info("ApplicationInit{}.costTime{}", desc, System.currentTimeMillis() - start);
            } catch (Exception e) {
                log.error("ApplicationInit{}.error", desc, e);
            }
        });
    }

    private void initFastJson() {
        SkuDO skuDO = new SkuDO();
        skuDO.setSkuId(1L);
        skuDO.setSkuName("苹果");
        String s = JSON.toJSONString(skuDO);
        System.out.println(s);
        JSON.parseObject(s, SkuDO.class);
    }

    interface InitFunction {
        void invoke();
    }

}
