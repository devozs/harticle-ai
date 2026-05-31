package com.devozs.components.harticle.dto.article;

import com.devozs.components.harticle.domain.ArticleType;
import com.devozs.components.harticle.domain.Reporter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String title;
    private String subTitle;
    private String content;
    private Reporter reporter;
    private ArticleType articleType;
    private boolean completed;
    private boolean faulted;
    private int votes;
    private String image;
}
