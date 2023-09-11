package com.jingdianjichi.user.db;

/**
 * 数据归档场景枚举
 *
 * @author: ChickenWing
 * @date: 2023/3/26
 */
public enum BackUpDataSceneEnum {

    USER_FORWARD("user_forward", "用户数据归档正向,由sys_user到sys_user_backup"),
    USER_BACKWARD("user_backward", "用户数据归档逆向,由sys_user_backup到sys_user");

    String code;

    String desc;

    BackUpDataSceneEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    /**
     * 根据code值获取渠道枚举
     */
    public static BackUpDataSceneEnum getByCode(String codeVal) {
        for (BackUpDataSceneEnum backUpDataSceneEnum : BackUpDataSceneEnum.values()) {
            if (codeVal.equals(backUpDataSceneEnum.code)) {
                return backUpDataSceneEnum;
            }
        }
        return null;
    }

}