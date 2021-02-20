package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class    HomeController {
    final private MemberService memberService;
    final private PostService postService;
    final private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception{
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postService", postService);
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String execSignup(MemberForm memberForm){
        memberService.createMember(memberForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
    public String execLogout() { return "redirect:/logout/result"; }
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
        return "admin";
    }
}
