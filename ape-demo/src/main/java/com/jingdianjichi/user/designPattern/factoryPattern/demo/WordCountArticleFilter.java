package com.jingdianjichi.user.designPattern.factoryPattern.demo;

/**
 * @Author: ChickenWing
 * @Description: 文章字数过滤器
 * @DateTime: 2022/12/4 23:22
 */
public class WordCountArticleFilter extends BaseArticleFilter {

    @Override
    public boolean doFilter(ArticleContext articleContext) {
        Article article = articleContext.getArticle();
        //执行当前字数过滤逻辑，来判断是否留下这篇文章
        //如果过滤了，可以走抽象类的公共方法
        if (true) {
            sendMsg();
        }
        return false;
    }

}
