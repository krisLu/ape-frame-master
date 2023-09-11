package com.jingdianjichi.user.designPattern.responsibilityPattern.base;

import com.jingdianjichi.user.designPattern.responsibilityChainPattern.ArticleInfo;
import com.jingdianjichi.user.designPattern.responsibilityChainPattern.RuleCheckContext;
import com.jingdianjichi.user.designPattern.responsibilityChainPattern.RuleCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <>
 *
 * @author zhengtong
 * @create 2021/9/24
 * @since 1.0.0
 */
@Component
@Slf4j
public class RuleCheckProcessorChain implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private BaseRuleCheckProcessor header;

    /**
     * 处理参数
     *
     * @param info 信息
     */
    public void processInfo(ArticleInfo info) {
        header.processInfo(info);
    }

    /**
     * 得到校验结果
     *
     * @return {@code List<String>}
     */
    public List<String> getRuleResult() {
        RuleCheckContext ruleCheckContext = header.RULE_CHECK_CONTEXT;
        RuleCheckResult ruleCheckResult = ruleCheckContext.getRuleCheckResult();
        List<String> failedMsgList = ruleCheckResult.getFailedMsgList();
        return header.RULE_CHECK_CONTEXT.getRuleCheckResult().getFailedMsgList();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        //region 责任链组装
        try {
            Map<String, BaseRuleCheckProcessor> name2Bean =
                    applicationContext.getBeansOfType(BaseRuleCheckProcessor.class);
            if (name2Bean.size() == 0) {
                return;
            }
            List<BaseRuleCheckProcessor> processors = new ArrayList<>(name2Bean.values());
            for (int i = 0; i < processors.size(); i++) {
                BaseRuleCheckProcessor processor = processors.get(i);
                if (i < processors.size() - 1) {
                    processor.setNextProcessor(processors.get(i + 1));
                }
            }
            header = processors.get(0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //endregion
    }

}
