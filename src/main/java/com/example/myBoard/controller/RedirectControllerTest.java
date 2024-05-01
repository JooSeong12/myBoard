package com.example.myBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/redirect")
public class RedirectControllerTest {
    String URL = "page";
    String REDIRECT_URL = "redirect:" + URL;

    @GetMapping("page")
    public String view(){
        return "/test/page";
    }

    @GetMapping("test1")
    public String test1(Model model){
        model.addAttribute("msg", REDIRECT_URL);
        return REDIRECT_URL; // return "redirect:page"
    }

    @GetMapping("test2")
    public ModelAndView test(Model model){
        model.addAttribute("msg", REDIRECT_URL);
        return new ModelAndView(REDIRECT_URL);
    }

    @GetMapping("test3")
    public RedirectView test3(Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg", REDIRECT_URL);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(URL);
        return redirectView;
    }
}
