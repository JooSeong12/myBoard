package com.example.myBoard.service;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Autowired
    Article article;
    @Test
    @DisplayName("전체데이터_읽기")
    void findList() {
        //Given
        int totalCount=3;
        //When
        List<ArticleDto> lists = articleService.findList();
        int actualCount = lists.size();
        //Then
        assertThat(actualCount).isEqualTo(totalCount);
    }

    @Test
    void detailList() {
    }

    @Test
    @DisplayName("자료수정_테스트")
    void update() {
        //Given
        ArticleDto expectDto = ArticleDto.builder().id(1L).title("라라라").content("444").build();
        //When
       articleService.update(new ArticleDto(1L,"라라라","444"));
       ArticleDto actualDto = articleService.findId(1L);
        //Then
        assertThat(actualDto.toString()).isEqualTo(expectDto.toString());
    }

    @Test
    @DisplayName("자료입력_테스트")
    void insert() {
        //Given
        ArticleDto expectDto = ArticleDto.builder().id(4L).title("라라라").content("444").build();
        //When
        articleService.insert(new ArticleDto(null, "라라라", "444"));
        //Then
        assertThat(articleService.findId(4L).getTitle()).isEqualTo("라라라");
        assertThat(articleService.findId(4L).getContent()).isEqualTo("444");
    }

    @Test
    @DisplayName("단건자료검색_테스트")
    void findId() {
        //Given
        ArticleDto expectDto = ArticleDto.builder().id(2L).title("나나나").content("222").build();
        //When
        ArticleDto actualDto = articleService.findId(2L);
        //Then
        assertThat(actualDto.toString()).isEqualTo(expectDto.toString());
    }
}