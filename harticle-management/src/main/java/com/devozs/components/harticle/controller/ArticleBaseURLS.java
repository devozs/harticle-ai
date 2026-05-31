package com.devozs.components.harticle.controller;

public final class ArticleBaseURLS {
    private ArticleBaseURLS() {}

    public static final String URL = "article";

    public static final String VOTE = "/vote";
    public static final String ARTICLE_ID = "/{articleId}";

    public static final String TITLE = "/{title}";

    public static final String SUB_TITLE = "/{subTitle}";

    public static final String CONTENT = "/{content}";

    public static final String IS_COMPLETED = "/{isCompleted}";

    public static final String IS_FAULTED = "/{isFaulted}";

    public static final String KEYWORDS = "/{keywords}";

    public static final String REPORTER = "/{reporter}";

    public static final String TEMPERATURE = "/{temperature}";

    public static final String URL_WITH_ID = ArticleBaseURLS.URL + ArticleBaseURLS.ARTICLE_ID;

}
