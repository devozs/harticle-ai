package com.devozs.components.harticle.repository;

import com.devozs.components.harticle.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {

    boolean existsByTitleIgnoreCase(String title);
    Optional<Article[]> findByTitleIgnoreCase(String title);
}