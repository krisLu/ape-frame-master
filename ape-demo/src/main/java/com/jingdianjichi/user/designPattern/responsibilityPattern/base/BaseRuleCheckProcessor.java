package com.jingdianjichi.user.designPattern.responsibilityPattern.base;

import com.jingdianjichi.user.designPattern.responsibilityChainPattern.ArticleInfo;
import com.jingdianjichi.user.designPattern.responsibilityChainPattern.RuleCheckContext;
import com.jingdianjichi.user.designPattern.responsibilityChainPattern.RuleCheckResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则校验基础处理器
 *
 * @author zhengtong
 * @create 2023/6/5
 * @date 2023/06/05
 * @since 1.0.0
 */
public abstract class BaseRuleCheckProcessor {

    /**
     * 下一个埋点处理类
     */
    private BaseRuleCheckProcessor nextProcessor;

    protected final static RuleCheckContext RULE_CHECK_CONTEXT = new RuleCheckContext();

    /**
     * 处理具体信息
     *
     * @param info 信息
     */
    public abstract void processInfo(ArticleInfo info);

    public void setNextProcessor(BaseRuleCheckProcessor baseRuleCheckProcessor) {
        this.nextProcessor = baseRuleCheckProcessor;
    }

    public BaseRuleCheckProcessor getNextProcessor() {
        return nextProcessor;
    }

    /**
     * 添加失败的信息
     */
    protected void addFailedMsg(String message) {
        RuleCheckResult ruleCheckResult = RULE_CHECK_CONTEXT.getRuleCheckResult();
        List<String> failedMsgList = RULE_CHECK_CONTEXT.getRuleCheckResult().getFailedMsgList();
        if (failedMsgList == null) {
            failedMsgList = new ArrayList<>();
            ruleCheckResult.setFailedMsgList(failedMsgList);
        }
        failedMsgList.add(message);
    }

}
