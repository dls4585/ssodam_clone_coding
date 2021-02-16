package ssodam.ssodam.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.service.MemberService;

@Controller
@AllArgsConstructor
public class HomeController {
    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
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
        return "loginSuccess";
    }

    @GetMapping("/logout/result")
    public String logout() {
        return "logout";
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