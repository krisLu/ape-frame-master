package com.jingdianjichi.user.designPattern.factoryPattern.demo;

public interface IArticleFilter {

    boolean doFilter(ArticleContext articleContext);

    // 可以扩展一些其他的方法

}
