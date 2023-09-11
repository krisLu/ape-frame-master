package com.jingdianjichi.user.designPattern.factoryPattern.demo;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleFilterFactory {

    private ArticleFilterFactory() {

    }

    public static List<IArticleFilter> createArticleFilter(List<ArticleFilterEnum> articleFilterEnumList) {
        List<IArticleFilter> articleFilterList = null;
        if (CollectionUtils.isEmpty(articleFilterEnumList)) {
            articleFilterList = articleFilterEnumList.stream().map(x ->
                    ArticleFilterFactory.createFilter(x)
            ).collect(Collectors.toList());
        }
        return articleFilterList;
    }

    private static IArticleFilter createFilter(ArticleFilterEnum articleFilterEnum) {
        IArticleFilter iArticleFilter = null;
        switch (articleFilterEnum) {
            case WORD_COUNT:
                iArticleFilter = new WordCountArticleFilter();
                break;
            default:
                break;
        }
        return iArticleFilter;
    }

}
