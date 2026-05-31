package com.devozs.components.harticle.controller;

import com.devozs.components.harticle.dto.article.ArticleDto;
import com.devozs.components.harticle.domain.Reporter;
import com.devozs.components.harticle.entity.Article;
import com.devozs.components.harticle.exception.ArticleNotExistsException;
import com.devozs.components.common.exception.DataKubeJobDeploymentException;
import com.devozs.components.harticle.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(ArticleBaseURLS.URL)
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }


    @GetMapping
    @ResponseBody
    public List<Article> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return articles;
    }

    @GetMapping(ArticleBaseURLS.ARTICLE_ID)
    @ResponseBody
    public Article getArticle(@PathVariable UUID articleId) throws ArticleNotExistsException {
        return articleService.getArticleById(articleId);
    }

    @DeleteMapping(ArticleBaseURLS.ARTICLE_ID)
    @ResponseStatus(HttpStatus.OK)
    public String deleteArticle(@PathVariable UUID articleId) throws ArticleNotExistsException {
        articleService.deleteArticle(articleId);
        return "Article: " + articleId + " deleted successfully.";
    }

    @PutMapping(ArticleBaseURLS.ARTICLE_ID)
    @ResponseBody
    public Article editArticle(@PathVariable UUID articleId, @RequestBody ArticleDto article) throws ArticleNotExistsException {
        log.info(String.format("controller edit method %s", articleId));
        return articleService.edit(articleId, article.getTitle(), article.getSubTitle(), article.getContent(), article.isCompleted(), article.isFaulted());
    }

    @PutMapping(ArticleBaseURLS.VOTE + ArticleBaseURLS.ARTICLE_ID)
    @ResponseBody
    public Article vote(@PathVariable UUID articleId) throws ArticleNotExistsException {
        return articleService.vote(articleId);
    }

    @PostMapping(ArticleBaseURLS.KEYWORDS + ArticleBaseURLS.REPORTER + ArticleBaseURLS.TEMPERATURE)
    @ResponseBody
    public Article createArticle(@PathVariable String keywords,
                                 @PathVariable Reporter reporter,
                                 @PathVariable int temperature) throws FileNotFoundException, DataKubeJobDeploymentException {
        return articleService.addArticle(keywords, reporter, temperature);
    }

    @ResponseBody
    @GetMapping(ControllerURLS.IS_EXISTS + ArticleBaseURLS.TITLE)
    public boolean isExists(@PathVariable String  title) {
        return articleService.isArticleValid(title);
    }

    @ResponseBody
    @GetMapping(path = ControllerURLS.BY_NAME)
    public Article[] getArticleByTitle(@PathVariable String  title) {

        Optional<Article[]> article = articleService.getArticleByTitle(title);
        return article.isPresent()  ? article.get() :  null;
    }

}
