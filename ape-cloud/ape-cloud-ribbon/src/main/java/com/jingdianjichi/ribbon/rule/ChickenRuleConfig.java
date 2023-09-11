package com.jingdianjichi.ribbon.rule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChickenRuleConfig {

    @Bean
    public IRule chickenRule(){
        return new ChickenRule();
    }

}
