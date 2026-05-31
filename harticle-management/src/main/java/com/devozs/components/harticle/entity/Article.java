package com.devozs.components.harticle.entity;

import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.harticle.domain.Reporter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@AllArgsConstructor

public class Article extends BaseEntity {

    private String title;
    @Column(name = "sub_title")
    private String subTitle;
    private String keywords;
    private String content;
    private Reporter reporter;
    private boolean completed = false;
    private boolean faulted = false;
    private int votes;
    private String image;
    private int temperature;

}
