package com.example.myBoard.repository;

import com.example.myBoard.entity.Article;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void 자료입력테스트(){
//        Given
        Article expectArticle = new Article(4L, "라라라", "444");
//        When
        Article newArticle = new Article(null, "라라라", "444");
        articleRepository.save(newArticle);
//        Then
        Article actualArticle = articleRepository.findById(4L).orElse(null);
        assertThat(actualArticle.toString()).isEqualTo(expectArticle.toString());
    }


    @Test
    void 모든데이터검색_클래스_성공(){
//        Given
        Article article1 = new Article(1L,"가가가","111");
        Article article2 = new Article(2L,"나나나","222");
        Article article3 = new Article(3L,"다다다","333");
        List<Article> expectList = new ArrayList<>();
        expectList.add(article1);
        expectList.add(article2);
        expectList.add(article3);
//        when
        List<Article> resultLists = articleRepository.findAll();
//        then
        assertThat(resultLists.toString()).isEqualTo(expectList.toString());
    }

    @Test
    void 모든데이터검색_클래스_실패(){
//        Given
        Article article1 = new Article(1L,"라라라","111");
        Article article2 = new Article(2L,"나나나","222");
        Article article3 = new Article(3L,"다다다","333");
        List<Article> expectList = new ArrayList<>();
        expectList.add(article1);
        expectList.add(article2);
        expectList.add(article3);
//        when
        List<Article> resultLists = articleRepository.findAll();
//        then
        assertThat(resultLists.toString()).isNotEqualTo(expectList.toString());
    }

    @Test
    void 전체데이터검색_개수(){
//        given
        int expectCount = 3;
//        when
        int actualCount = articleRepository.findAll().size();
//        then
        assertThat(actualCount).isEqualTo(expectCount);
    }

    @Test
    void 자료삭제_테스트_성공(){
        //Given
        Long deleteId = 4L;
        //when
        articleRepository.deleteById(4L);
        //then
        Article actualResult = articleRepository.findById(4L).orElse(null);
        assertThat(actualResult).isEqualTo(null);
    }

    @Test
    void 자료수정_테스트(){
        //Given
        Article expectArticle = new Article(1L,"타이틀수정", "컨텐트수정");
        //when
        Article updateArticle = new Article(1L,"타이틀수정", "컨텐트수정");
        articleRepository.save(expectArticle);
        Article actualArticle = articleRepository.findById(1L).orElse(null);
        //then
        assertThat(actualArticle.toString()).isEqualTo(expectArticle.toString());
    }
}


