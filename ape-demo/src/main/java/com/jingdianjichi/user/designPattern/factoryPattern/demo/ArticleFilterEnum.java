package com.jingdianjichi.user.designPattern.factoryPattern.demo;

/**
 * @Author: ChickenWing
 * @Description: 文章过滤器枚举
 * @DateTime: 2022/11/19 20:36
 */
public enum ArticleFilterEnum {

    WORD_COUNT(0, "字数过滤");

    private int code;

    private String desc;

    ArticleFilterEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code值获取渠道枚举
     */
    public static ArticleFilterEnum getByCode(int codeVal) {
        for (ArticleFilterEnum channelEnum : ArticleFilterEnum.values()) {
            if (channelEnum.code == codeVal) {
                return channelEnum;
            }
        }
        return null;
    }

    /**
     * 根据code值获取desc
     */
    public static String getValueByCode(int code) {
        ArticleFilterEnum[] values = ArticleFilterEnum.values();
        for (ArticleFilterEnum channelEnum : values) {
            if (channelEnum.code == code) {
                return channelEnum.desc;
            }
        }
        return null;
    }


}
