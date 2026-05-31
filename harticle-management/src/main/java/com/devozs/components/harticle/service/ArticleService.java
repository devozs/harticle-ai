package com.devozs.components.harticle.service;

import com.devozs.components.common.service.TaskManagementService;
import com.devozs.components.common.utils.JobFileUtils;
import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.domain.Reporter;
import com.devozs.components.harticle.entity.Article;
import com.devozs.components.common.entity.flow.AsyncTask;
import com.devozs.components.harticle.exception.ArticleNotExistsException;
import com.devozs.components.common.exception.DataKubeJobDeploymentException;
import com.devozs.components.harticle.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.devozs.components.common.service.engine.DataKubeJobService;
@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final DataKubeJobService dataKubeJobService;

    TaskManagementService taskManagementService;

    @Value("${harticle.engine.use-kubernetes:true}")
    private boolean useKubernetesEngine;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          DataKubeJobService dataKubeJobService,
                          TaskManagementService taskManagementService) {
        this.articleRepository = articleRepository;
        this.dataKubeJobService = dataKubeJobService;
        this.taskManagementService = taskManagementService;
    }

    public Article addArticle(String keywords, Reporter reporter, int temperature) throws FileNotFoundException, DataKubeJobDeploymentException {
        AsyncTask asyncTask = taskManagementService.createNewTask();
        Article article = null;
        try {
            article = new Article();
            article.setKeywords(keywords);
            article.setReporter(reporter);
            article.setTemperature(temperature);
            article = articleRepository.save(article);
            log.info(String.format("creating new article job id %s", article.getId()));
            if (useKubernetesEngine) {
                dataKubeJobService.runDataKubeJob(asyncTask, article, JobFileUtils.getEngineFile());
            } else {
                dataKubeJobService.runDataKubeRest(asyncTask, article);
            }
        } catch (DataKubeJobDeploymentException
                 | FileNotFoundException
                e
        ) {
            taskManagementService.failTask(asyncTask, "kubejob deployment failed", ErrorType.COMMUNICATION);
            throw new DataKubeJobDeploymentException(e);
//            log.error(String.format("could not execute article creation job id %s", article.getId()));
//            throw e;
        }
        return article;
    }

    public boolean isArticleValid(String title) {
        return !articleRepository.existsByTitleIgnoreCase(title);
    }

    public Article edit(UUID id, String title, String subTitle, String content, boolean completed, boolean faulted) throws ArticleNotExistsException {
        log.info(String.format("service edit method start %s", id));
        final Article article = getArticleById(id);
        if(title != null && !title.isBlank())
            article.setTitle(title);
        if(subTitle != null && !subTitle.isBlank())
            article.setSubTitle(subTitle);
        if(content != null && !content.isBlank())
            article.setContent(content);
        article.setCompleted(completed);
        article.setFaulted(faulted);
        log.info(String.format("service edit method end %s", id));
        return articleRepository.save(article);
    }

    public Article vote(UUID id) throws ArticleNotExistsException {
        final Article article = getArticleById(id);
        article.setVotes(article.getVotes() + 1);
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return StreamSupport.stream(this.articleRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Article> getAllArticles(Iterable<UUID> ids) {
        return StreamSupport.stream(this.articleRepository.findAllById(ids).spliterator(), false).collect(Collectors.toList());
    }

    public void deleteArticle(UUID articleId) throws ArticleNotExistsException {
        final Article article = getArticleById(articleId);
        articleRepository.delete(article);
    }


    public Article getArticleById(UUID articleId) throws ArticleNotExistsException {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotExistsException(articleId));
    }


    public Optional<Article[]> getArticleByTitle(String title) {
        return articleRepository.findByTitleIgnoreCase(title);

    }

}
