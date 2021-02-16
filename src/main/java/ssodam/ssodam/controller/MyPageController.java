package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CommentRepository;
import ssodam.ssodam.service.CommentService;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

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
        // 현재 비밀번호랑 새 비밀번호랑 다르도록 검사해야함.
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
    public String myContents(Model model, @AuthenticationPrincipal Member currentMember) {
        List<Post> posts = currentMember.getPosts();
        model.addAttribute("posts", posts);
        return "mypage/contents";
    }

    @GetMapping("/comments")
    public String myComments(Model model, @AuthenticationPrincipal Member currentMember) {
        List<Comment> comments = currentMember.getComments();
        model.addAttribute("comments", comments);
        return "mypage/comments";
    }

    @PostMapping("/{post_id}/comments")
    public String writeComment(@PathVariable("post_id") Long postId, CommentForm form, @AuthenticationPrincipal Member currentMember) {
        commentService.writeComment(currentMember.getId(), postId, form.getContents());
        return "redirect:/"+postId;
    }

    @PostMapping("/{post_id}/{comment_id}")
    public String writeSubComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId,
                                  CommentForm form,@AuthenticationPrincipal Member currentMember){
        Post post = postService.findOne(postId);
        commentService.writeSubcomment(currentMember.getId(), commentId, form.getContents());
        return "redirect:/" + postId + commentId;
    }

    @GetMapping("/{post_id}/{comment_id}/update")
    public String updateCommentView(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, Model model) {
        CommentForm form = new CommentForm();
        Comment comment = commentRepository.getOne(commentId);
        form.setContents(comment.getContent());
        // 모델 추가? 해당 포스트의 댓글 목록 다시?
        model.addAttribute("commentForm", form);
        return "redirect:/" + postId;
    }

    @PatchMapping("/{post_id}/{comment_id}/update")
    public String updateComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId,
                                CommentForm form, @AuthenticationPrincipal Member currentMember) {
        commentService.updateComment(currentMember.getId(), commentId, form.getContents());
        return "redirect:/" + postId;
    }

    @DeleteMapping("/{post_id}/{comment_id}/delete")
    public String deleteComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId,
                                @AuthenticationPrincipal Member currentMember) {
        commentService.deleteComment(currentMember.getId(), postId, commentId);
        return "redirect:/" + postId;
    }
}
