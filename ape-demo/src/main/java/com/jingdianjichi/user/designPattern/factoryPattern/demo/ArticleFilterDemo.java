package com.jingdianjichi.user.designPattern.factoryPattern.demo;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleFilterDemo {

    private List<ArticleFilterEnum> articleFilterEnumList;

    private List<IArticleFilter> iArticleFilterList;

    public ArticleFilterDemo() {
        init();
    }

    private void init() {
        this.iArticleFilterList = ArticleFilterFactory.createArticleFilter(articleFilterEnumList);
    }

    public void doFilter(List<Article> articleList) {
        List<Article> collect = articleList.stream().filter(article -> {
            ArticleContext articleContext = new ArticleContext();
            articleContext.setArticle(article);
            return doFilter(articleContext);
        }).collect(Collectors.toList());
        //collect就是过滤后的文章，可以留下继续使用。
    }

    private boolean doFilter(ArticleContext articleContext) {
        for (IArticleFilter iArticleFilter : iArticleFilterList) {
            if (iArticleFilter.doFilter(articleContext)) {
                return false;
                //此时被过滤跳出循环
            }
        }
        return true;
    }


}
