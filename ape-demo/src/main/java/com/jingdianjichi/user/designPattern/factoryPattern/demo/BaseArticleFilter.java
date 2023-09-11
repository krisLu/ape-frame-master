package com.jingdianjichi.user.designPattern.factoryPattern.demo;

public abstract class BaseArticleFilter implements IArticleFilter{

    /**
     * 可以自由进行扩展，例如文章被过滤后，都统一发个消息通知
     */
    void sendMsg(){
        //发送消息通知的逻辑
    }


}
