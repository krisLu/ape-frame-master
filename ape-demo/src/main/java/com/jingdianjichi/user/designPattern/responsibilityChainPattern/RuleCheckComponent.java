package com.jingdianjichi.user.designPattern.responsibilityChainPattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class RuleCheckComponent {

    public RuleCheckResult checkArticle(ArticleInfo articleInfo) {
        RuleCheckContext context = new RuleCheckContext();
        // 校验规则具有顺序
        return this.checkTitle(articleInfo)
                .andThen(this.checkContentLength(articleInfo))
                .apply(context)
                .getRuleCheckResult();
    }

    private Function<RuleCheckContext, RuleCheckContext> checkTitle(ArticleInfo articleInfo) {
        return buildCheck(titleCheckContext -> {
            if (titleCheckContext.hasError()) {
                return;
            }
            String title = articleInfo.getTitle();
            if (title.contains("jingdian")) {
                addFailedMsg(titleCheckContext.getRuleCheckResult(), "标题不能包含jingdian");
                return;
            }
        });
    }

    private Function<RuleCheckContext, RuleCheckContext> checkContentLength(ArticleInfo articleInfo) {
        return buildCheck(contentCheckContext -> {
            if (contentCheckContext.hasError()) {
                return;
            }
            String content = articleInfo.getContent();
            if (content.length() > 10) {
                addFailedMsg(contentCheckContext.getRuleCheckResult(), "内容长度不能大于10");
                return;
            }
        });
    }

    private static <T> Function<T, T> buildCheck(Consumer<T> checkConsumer) {
        return checkContext -> {
            checkConsumer.accept(checkContext);
            return checkContext;
        };
    }

    /**
     * 添加失败的信息
     */
    public void addFailedMsg(RuleCheckResult checkResult, String message) {
        List<String> failedMsgList = checkResult.getFailedMsgList();
        if (failedMsgList == null) {
            failedMsgList = new ArrayList<>();
            checkResult.setFailedMsgList(failedMsgList);
        }
        failedMsgList.add(message);
    }


}