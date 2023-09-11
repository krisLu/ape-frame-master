package com.jingdianjichi.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class HomeController {

    @Resource
    private RestTemplate restTemplate;

    private static final String GET_SKU_INFO_URL = "http://APE-CLOUD-SKU/test";

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/getSkuInfo")
    public String getSkuInfo() {
        return restTemplate.getForObject(GET_SKU_INFO_URL,String.class);
    }

}
