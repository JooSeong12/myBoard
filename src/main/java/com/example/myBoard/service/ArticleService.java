package com.example.myBoard.service;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import com.example.myBoard.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

   public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
   }

   public List findList(){
       List<ArticleDto> dtoList = articleRepository.findAll().stream()
               .map(x-> ArticleDto.fromArticleEntity(x)).toList();
       return dtoList;
   }

   public List detailList(Long id){
       List<ArticleDto> dtoList = articleRepository.findById(id).stream()
               .map(x-> ArticleDto.fromArticleEntity(x)).toList();
       return dtoList;
   }

   public void update(ArticleDto dto){
       Article article = dto.fromArticleDto(dto);
       articleRepository.save(article);
   }

   public void insert(ArticleDto dto){
       Article article = dto.fromArticleDto(dto);
       articleRepository.save(article);
   }
    public ArticleDto findId(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        ArticleDto dto = ArticleDto.fromArticleEntity(article);
        return dto;
    }
}
