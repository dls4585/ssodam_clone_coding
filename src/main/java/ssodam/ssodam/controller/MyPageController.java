package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.domain.Scrap;
import ssodam.ssodam.repository.CommentRepository;
import ssodam.ssodam.service.CommentService;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/me")
    public String myPageHome(Model model, @AuthenticationPrincipal Member currentMember) {
        MemberForm memberForm = new MemberForm();
        memberForm.setName(currentMember.getUsername());
        memberForm.setEmail(currentMember.getEmail());
        model.addAttribute("memberForm", memberForm);
        return "mypage/me";
    }

    @PostMapping("/me")
    public String userEdit(MemberForm form, BindingResult result, @AuthenticationPrincipal Member currentMember) {
        if(result.hasErrors()) {
            return "redirect:/me";
        }
        memberService.updateName(currentMember.getUsername(), form.getName());
        currentMember.setUsername(form.getName());

        return "redirect:/me";
    }

    @GetMapping("/password")
    public String passwordView(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        return "mypage/password";
    }

    @PostMapping("/password")
    public String passwordEdit(Model model,
                               PasswordForm form,
                               BindingResult result,
                               @AuthenticationPrincipal Member currentMember) {
        if (result.hasErrors()) {
            return "redirect:/password";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(form.getPassword(), currentMember.getPassword())) {
            model.addAttribute("error", "현재 패스워드 불일치");
            return "mypage/passwordError";
        }

        if(form.getNewPassword().equals(form.getPassword())){
            model.addAttribute("error", "동일한 패스워드");
            return "mypage/passwordError";
        }

        if (!form.getNewPassword().equals(form.getRetype())) {
            model.addAttribute("error", "새 패스워드 불일치");
            return "mypage/passwordError";
        }

        String encodedNewPwd = encoder.encode(form.getNewPassword());
        memberService.updatePassword(currentMember.getUsername(), encodedNewPwd);
        currentMember.setPassword(encodedNewPwd);
        return "redirect:/me";
    }

    @GetMapping("/contents")
    public String myContents(Model model, @AuthenticationPrincipal Member currentMember,
                             @PageableDefault Pageable pageable) {
        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();
        Page<Post> posts = postService.getPostListByMember(member, pageable);
        model.addAttribute("posts", posts);
        return "mypage/contents";
    }

    @GetMapping("/comments")
    public String myComments(Model model, @AuthenticationPrincipal Member currentMember,
                             @PageableDefault Pageable pageable) {
        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();
        Page<Comment> comments = commentService.findListByMember(member, pageable);
        model.addAttribute("comments", comments);
        return "mypage/comments";
    }

    @GetMapping("/scrap")
    public String myScrap(Model model,
                          @AuthenticationPrincipal Member currentMember,
                          @PageableDefault Pageable pageable) {
        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();
        Set<Scrap> scraps = member.getScraps();
        System.out.println("scraps = " + scraps);
        List<Post> scrappedPosts = new ArrayList<>();
        for(Scrap scrap : scraps) {
            scrappedPosts.add(scrap.getPost());
        }

        int page = (pageable.getPageNumber()==0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page,10, Sort.by("id").descending());

        Page<Post> posts = new PageImpl<Post>(scrappedPosts, pageable, scraps.size());
        model.addAttribute("posts", posts);

        return "mypage/scrap";
        
    @GetMapping("/deleteMember")
    public String delMember(Model model, String checkWords){
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("checkWords", checkWords);

        return "mypage/deleteMember";
    }

    @PostMapping("/deleteMember")
    public String delMember(PasswordForm form,
                            String checkWords,
                            @AuthenticationPrincipal Member currentMember){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(form.getPassword(), currentMember.getPassword())) {
            System.out.println("비밀번호 불일치");
            return "redirect:/deleteMember";
        }
        else{
            System.out.println("비밀번호 일치");
        }

        if(!checkWords.equals("Delete Member")){
            System.out.println("문장 불일치");
            return "redirect:/deleteMember";
        }
        else{
            System.out.println("문장 일치");
        }

        Member member = memberService.findByUsername(currentMember.getUsername()).get();
        memberService.deleteMember(member.getUsername());

        return "redirect:/logout";
    }
}
