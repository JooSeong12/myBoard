package com.example.myBoard.controller;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.repository.ArticleRepository;
import com.example.myBoard.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    public MainController(ArticleRepository articleRepository, ArticleService articleService){
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }


    @GetMapping("/")
    public String testView(Model model){
        List<ArticleDto> dtoList = articleService.findList();
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("name", "김주성");
        return "articles/show_all";
    }

}
