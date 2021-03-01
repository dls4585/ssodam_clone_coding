package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.service.CategoryService;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("member")
public class HomeController {
    final private MemberService memberService;
    final private PostService postService;
    final private CategoryService categoryService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postService", postService);
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "login/signup";
    }

    @PostMapping("/signup")
    public String execSignup(MemberForm memberForm) {
        if (memberService.findByEmail(memberForm.getEmail()).isPresent()) {
            System.out.println("이미 존재하는 이메일");
            return "login/duplicate";
        } else if (memberService.findByUsername(memberForm.getUsername()).isPresent()) {
            System.out.println("이미 존재하는 아이디");
            return "login/duplicate";
        } else {
            memberService.createMember(memberForm);
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public String execLogin() {
        return "redirect:/login/result";
    }

    @GetMapping("/login/result")
    public String loginResult() {
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String execLogout() {
        return "redirect:/logout/result";
    }

    @GetMapping("/logout/result")
    public String logoutResult() {
        return "redirect:/home";
    }


    @GetMapping("/fail")
    @ResponseBody
    public String fail() {
        return "FAILED";
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

    // 어드민 페이지
    @GetMapping("admin")
    public String admin() {
        return "admin/admin";
    }
}