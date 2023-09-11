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
public class AuthorCheckProcessor extends BaseRuleCheckProcessor {
    @Override
    public void processInfo(ArticleInfo info) {
        checkAuthorRule(info);
        // getNextProcessor().processInfo(info);
        BaseRuleCheckProcessor nextProcessor = getNextProcessor();
        Optional.ofNullable(nextProcessor).ifPresent((t) -> nextProcessor.processInfo(info));
    }

    private void checkAuthorRule(ArticleInfo info) {
        if (RULE_CHECK_CONTEXT.hasError()) {
            return;
        }
        String author = info.getAuthor();
        if (author.contains("zhengtong")) {
            addFailedMsg("author包含zhengtong");
        }
    }
}
