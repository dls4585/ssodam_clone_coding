package ssodam.ssodam.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class HomeController {
    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
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
    public String execLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("member", session.getId());
        return "redirect:/user/login/result";
    }
    @GetMapping("user/login/result")
    public String loginResult() {
        return "loginSuccess";
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