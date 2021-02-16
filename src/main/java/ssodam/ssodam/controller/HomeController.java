package ssodam.ssodam.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    @Autowired private MemberService memberService;
    @Autowired private PostService postService;
    @Autowired private CategoryRepository categoryRepository;

    @GetMapping("/home")
    public String home(Model model) throws Exception{
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postService", postService);
        return "home";
    }

    @GetMapping("user/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("user/signup")
    public String execSignup(MemberForm memberForm){
        memberService.createMember(memberForm);
        return "redirect:/user/login";
    }

    @GetMapping("user/login")
    public String login() {
        return "login";
    }

    @PostMapping("user/login")
    public String execLogin() {
        return "redirect:/user/login/result";
    }
    @GetMapping("user/login/result")
    public String loginResult() {
        return "home";
    }

    @GetMapping("user/logout/result")
    public String logout() {
        return "logout";
    }

    // 접근 거부 페이지
    @GetMapping("user/denied")
    public String denied() {
        return "denied";
    }

    // 내 정보 페이지
    @GetMapping("user/info")
    public String myInfo() {
        return "myinfo";
    }

    // 어드민 페이지
    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}