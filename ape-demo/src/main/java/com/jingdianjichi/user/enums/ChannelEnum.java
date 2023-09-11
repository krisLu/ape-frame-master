package com.jingdianjichi.user.enums;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: ChickenWing
 * @Description: 渠道枚举
 * @DateTime: 2022/11/19 20:36
 */
@Getter
public enum ChannelEnum {

    DOU_YIN(0, "抖音渠道"),
    BILIBILI(1, "B站渠道");

    private int code;

    private String desc;

    ChannelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final Map<Integer, ChannelEnum> clientChannelMap = Stream.of(ChannelEnum.values())
            .collect(Collectors.toMap(e -> e.getCode(), e -> e));

    public static Integer getCode(String channel) {
        if (StringUtils.isBlank(channel)) {
            return null;
        }
        ChannelEnum clientChannel = clientChannelMap.get(channel);
        if (null == clientChannel) {
            return null;
        }
        return clientChannel.getCode();
    }

    /**
     * 根据code值获取渠道枚举
     */
    public static ChannelEnum getByCode(int codeVal) {
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
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
        ChannelEnum[] values = ChannelEnum.values();
        for (ChannelEnum channelEnum : values) {
            if (channelEnum.code == code) {
                return channelEnum.desc;
            }
        }
        return null;
    }

}
