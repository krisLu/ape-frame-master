package com.jingdianjichi.user.designPattern.responsibilityPattern;

import com.jingdianjichi.user.designPattern.responsibilityChainPattern.ArticleInfo;
import com.jingdianjichi.user.designPattern.responsibilityPattern.base.BaseRuleCheckProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <标题校验处理器>
 *
 * @author zhengtong
 * @create 2023/6/5
 * @since 1.0.0
 */
@Component
public class TitleCheckProcessor extends BaseRuleCheckProcessor {
    @Override
    public void processInfo(ArticleInfo info) {
        checkTitleRule(info);
        BaseRuleCheckProcessor nextProcessor = getNextProcessor();
        Optional.ofNullable(nextProcessor).ifPresent((t) -> nextProcessor.processInfo(info));    }

    private void checkTitleRule(ArticleInfo info) {
        if (RULE_CHECK_CONTEXT.hasError()) {
            return;
        }
        String title = info.getTitle();
        if (title.contains("jingdian")) {
            addFailedMsg("标题不能包含jingdian");
        }
    }
}
