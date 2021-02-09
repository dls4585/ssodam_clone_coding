package ssodam.ssodam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberDetails;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
public class MyPageController {

    MemberService memberService;
    MemberRepository memberRepository;

    @GetMapping("/me")
    public String myPageHome(Model model,
                             Authentication authentication, HttpServletRequest request) {
        log.debug("Authentication : {}", authentication);
        log.debug("Request : {}", request);
        if(authentication == null) {
            return "login";
        }
        if(authentication.getPrincipal() == null) {
            return "hello";
        }
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();
        String memberName = principal.getUsername();
        Optional<Member> member = memberRepository.findByUsername(memberName);
        int totalLikes = 0;
        int totalDislikes = 0;
        for(Post post : member.get().getPosts()){
            totalLikes += post.getLikes();
            totalDislikes += post.getDislikes();
        }
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("member", member.get());
        model.addAttribute("likes", totalLikes);
        model.addAttribute("dislikes", totalDislikes);
        return "mypage/me";
    }

    @PostMapping("/me")
    public String userEdit(@Valid MemberForm form, BindingResult result,
                           @AuthenticationPrincipal MemberDetails memberDetails) {
        if(result.hasErrors()) {
            return "redirect:/me";
        }
        String memberName = memberDetails.getUsername();
        Optional<Member> optionalMember = memberRepository.findByUsername(memberName);
        Member member = optionalMember.get();
        member.setUsername(form.getName());
        return "redirect:/me";
    }

    @GetMapping("/password")
    public String passwordEdit(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());

        return "mypage/password";
    }
}
