package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FindPasswordController {
    final private MemberService memberService;

    @RequestMapping(value = "/findPassword", method = RequestMethod.GET)
    public String findPassword() {
        return "login/findPassword";
    }

    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public String checkUser(MemberForm memberForm) {
        System.out.println("memberForm.getName() = " + memberForm.getName());
        System.out.println("memberForm.getEmail() = " + memberForm.getEmail());

        Optional<Member> optionalMember = memberService.findByEmail(memberForm.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            //이름, 이메일 둘 다 맞을 경우
            if (member.getUsername().equals(memberForm.getName())) {
                System.out.println("checkUser");
                System.out.println("memberForm.getName() = " + memberForm.getName());
                System.out.println("memberForm.getName() = " + memberForm.getName());
                return "forward:/findPasswordSuccess";
            }
            //이메일은 맞지만 이름이 틀린 경우: 비번 찾기 실패
            else {
                return "redirect:wrongName";
            }
        }
        //존재하지 않는 이메일
        else {
            System.out.println("존재 안하는 이름");
            return "redirect:wrongEmail";
        }
    }

    @RequestMapping(value = "/findPasswordSuccess", method = RequestMethod.POST)
    public String findPasswordSuccess(Model model, MemberForm memberForm) {
        System.out.println("findPasswordSuccess");
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("memberForm", memberForm);
        return "login/findPasswordSuccess";
    }



    @GetMapping("wrongEmail")
    public String findPasswordWrongEmail() {
        return "login/findPasswordWrongEmail";
    }



    @GetMapping("wrongName")
    public String findPasswordWrongName() {
        return "login/findPasswordWrongName";
    }




    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(String username, PasswordForm passwordForm) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!passwordForm.getNewPassword().equals(passwordForm.getRetype())) {
            return "redirect:";
        }

        Member member = memberService.findByUsername(username).get();
        System.out.println("member.getPassword() = " + member.getPassword());
        String encodedNewPwd = encoder.encode(passwordForm.getNewPassword());

        memberService.updatePassword(username, encodedNewPwd);
        System.out.println("member.getPassword() = " + member.getPassword());

        return "redirect:/home";
    }
}
