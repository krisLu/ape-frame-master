package com.jingdianjichi.user.designPattern.responsibilityChainPattern;

import lombok.Data;

import java.util.List;

@Data
public class RuleCheckResult {

    /**
     * 失败信息列表
     */
    private List<String> failedMsgList;

}