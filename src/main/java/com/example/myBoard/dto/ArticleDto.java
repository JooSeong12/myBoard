package com.example.myBoard.dto;

import com.example.myBoard.entity.Article;
import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public static ArticleDto fromArticleEntity(Article article){
        return new ArticleDto(article.getId(), article.getTitle(), article.getContent());
    }

    public Article fromArticleDto(ArticleDto dto){
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return article;
    }
}
