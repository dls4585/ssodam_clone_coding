package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssodam.ssodam.domain.*;
import ssodam.ssodam.service.CategoryService;
import ssodam.ssodam.service.CommentService;
import ssodam.ssodam.service.MemberService;
import ssodam.ssodam.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
// http://www.ssodam.com/content/1099804?prev=3&prev_content=/board/3
//                                postId?이전페이지&카테고리
public class PostController {
    private final MemberService memberService;
    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping("content/{postId}")
    public String board(@PathVariable("postId") Long postId,
                        @RequestParam("prev") Long prev,
                        @RequestParam("prev_content") String prev_content,
                        Model model){

        Post post = postService.findOne(postId);
        System.out.println("prev = " + prev);
        System.out.println("prev_content = " + prev_content);   //분리


        model.addAttribute("post", post);
        model.addAttribute("commentForm", new CommentForm());
        return "content";       //이따 만들자
    }

    @GetMapping("/board/{categoryId}")
    public String postView(@PathVariable("categoryId") Long categoryId, @PageableDefault Pageable pageable, Model model) {
        Category category = categoryService.findOne(categoryId);
        Page<Post> boardList = postService.getPostListByCategory(category, pageable);
        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryName", categoryId);
        return "board";
    }

    @GetMapping("/write/{categoryId}")
    public String post(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = categoryService.findOne(categoryId);
        model.addAttribute("categoryName", categoryId);
        return "write";
    }

    @PostMapping("/write/{categoryId}")
    public String writePost(@PathVariable("categoryId") Long categoryId, PostForm postForm){
        Category category = categoryService.findOne(categoryId);
        postForm.setCategory(category);
        postService.post(postForm);

        return "redirect:/board/{categoryId}";
    }

    @PostMapping("/content/content/{postId}/comments")
    public String writeComment(@PathVariable("postId") Long postId,
                               HttpServletRequest request,
                               CommentForm form,
                               @AuthenticationPrincipal Member currentMember) {

        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();

        commentService.writeComment(member.getId(), postId, form.getContents());

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @PostMapping("content/content/subComment/{commentId}")
    public String writeSubComment(@PathVariable("commentId") Long commentId,
                                  @AuthenticationPrincipal Member currentMember,
                                  HttpServletRequest request){

        String content = request.getParameter("content");

        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();

        commentService.writeSubcomment(member.getId(), commentId, content);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }


    @PatchMapping("content/content/update/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @AuthenticationPrincipal Member currentMember,
                                HttpServletRequest request) {
        String content = request.getParameter("content");

        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();

        commentService.updateComment(member.getId(), commentId, content);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @DeleteMapping("content/content/delete/{commentId}/")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @AuthenticationPrincipal Member currentMember,
                                HttpServletRequest request) {
        Long postId = commentService.findOne(commentId).getPost().getId();

        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();

        commentService.deleteComment(member.getId(), postId, commentId);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
