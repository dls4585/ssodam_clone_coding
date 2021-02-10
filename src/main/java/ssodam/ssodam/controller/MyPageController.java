package ssodam.ssodam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.service.MemberService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MyPageController {

    MemberService memberService;
    MemberRepository memberRepository;

    @GetMapping("/me")
    public String myPageHome(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "mypage/me";
    }

    @PostMapping("/me")
    public String userEdit(@Valid MemberForm form, BindingResult result, Authentication authentication) {
        if(result.hasErrors()) {
            return "mypage/me";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Member> optionalMember = memberRepository.findByUsername(userDetails.getUsername());
        Member member = optionalMember.get();
        member.setUsername(form.getName());
        return "redirect:/mypage/me";
    }
}
