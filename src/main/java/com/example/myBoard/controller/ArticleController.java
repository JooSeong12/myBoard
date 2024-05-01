package com.example.myBoard.controller;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import com.example.myBoard.repository.ArticleRepository;
import com.example.myBoard.service.ArticleService;
import com.example.myBoard.service.PaginationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    PaginationService paginationService;
    @GetMapping("paging")
    public String mainView(Model model,
                           @PageableDefault(page = 0, size = 10, sort = "id",
                           direction = Sort.Direction.DESC)Pageable pageable){
        //넘겨온 페이지 번호로 리스트 받아오기
       Page<Article> paging = articleService.pagingList(pageable);
        Page<Article> paging2 = articleRepository.findAll(pageable);
       // 페이지 블럭 처리(1, 2, 3, 4, 5)
       int totalPage = paging.getTotalPages();
       List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), totalPage);

       model.addAttribute("paginationBarNumbers", barNumbers);
       model.addAttribute("paging", paging);
        return "articles/show_all_list";
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
    public String delete(@RequestParam("deleteId") Long id,
                         RedirectAttributes redirectAttributes){
        // 1. 삭제할 대상이 존재하는지 확인
        ArticleDto dto = articleService.findId(id);
        //2. 대상 엔티티가 존재하면 삭제처리 후 메세지를 전송
        if(dto != null){
            articleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        }
        return "redirect:/";
    }
}
