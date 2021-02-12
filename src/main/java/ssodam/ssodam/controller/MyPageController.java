package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/me")
    public String myPageHome(Model model, @AuthenticationPrincipal Member currentMember) {
        MemberForm memberForm = new MemberForm();
        memberForm.setName(currentMember.getUsername());
        model.addAttribute("memberForm", memberForm);
        return "mypage/me";
    }

    @PostMapping("/me")
    public String userEdit(MemberForm form, BindingResult result, @AuthenticationPrincipal Member currentMember) {
        if(result.hasErrors()) {
            return "mypage/me";
        }
        memberService.updateName(currentMember.getUsername(), form.getName());
        System.out.println("currentMember = " + currentMember.getUsername());
        currentMember.setUsername(form.getName());

        return "redirect:/me";
    }

}
