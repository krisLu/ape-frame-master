package com.jingdianjichi.user.designPattern.builderPattern.demo;

public interface SkuVOFunction<T extends SkuVO> {

    T newInstance();

}
