package com.example.myBoard.controller;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import com.example.myBoard.repository.ArticleRepository;
import com.example.myBoard.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    public ArticleController(ArticleRepository articleRepository, ArticleService articleService){
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        List<ArticleDto> dtoList = articleService.detailList(id);
        model.addAttribute("dtoList", dtoList);
        return "/articles/detail";
    }
    @GetMapping("insert")
    public String insertView(Model model){
        model.addAttribute("dto", new ArticleDto());
        return "/articles/new";
    }

    @PostMapping("insert")
    public String insertPost(@ModelAttribute("dto") ArticleDto dto){
        articleService.insert(dto);
        return "redirect:/";
    }
    @GetMapping("update")
    public String update(@RequestParam("id") Long id, Model model){
        ArticleDto dto = articleService.findId(id);
        model.addAttribute("dto", dto);
        return "/articles/update";
    }
    @PostMapping("update")
    public String updatePost(@ModelAttribute("dto") ArticleDto dto){
        articleService.update(dto);
        return "redirect:/";
    }
    @PostMapping("delete")
    public String delete(@RequestParam("deleteId") Long id){
        articleRepository.deleteById(id);
        return "redirect:/";
    }
}
