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
import java.util.List;
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
    public String postView(@PathVariable("postId") Long postId,
                        @RequestParam("prev") Long prev,
                        @RequestParam("prev_content") String prev_content,
                        Model model){

        Post post = postService.findOne(postId);
        postService.increaseVisit(post);
      
        Long categoryId = Long.parseLong(prev_content.substring(7));

        model.addAttribute("post", post);
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("prev_content", categoryId);
        model.addAttribute("prev", prev);
      
        return "post/content";
    }

    @GetMapping("/board/{categoryId}")
    public String board(@PathVariable("categoryId") Long categoryId, @PageableDefault Pageable pageable, Model model) {
        Category category = categoryService.findOne(categoryId);
        Page<Post> postList = postService.getPostListByCategory(category, pageable);
        model.addAttribute("boardList", postList);
        model.addAttribute("category", category);
        return "post/board";
    }

    @GetMapping("/write/{categoryId}")
    public String post(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = categoryService.findOne(categoryId);
        model.addAttribute("categoryName", categoryId);
        return "post/write";
    }

    @PostMapping("/write/{categoryId}")
    public String writePost(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal Member currentMember, PostForm postForm){
        Category category = categoryService.findOne(categoryId);
        postForm.setCategory(category);
        postForm.setMember(currentMember);
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

    @PostMapping("content/content/update/{commentId}")
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

    @PostMapping("content/content/delete")
    public String deleteComment(@AuthenticationPrincipal Member currentMember,
                                HttpServletRequest request) {

        String comment = request.getParameter("commentId");
        Long commentId = Long.parseLong(comment);

        Long postId = commentService.findOne(commentId).getPost().getId();

        Optional<Member> optionalMember = memberService.findByUsername(currentMember.getUsername());
        Member member = optionalMember.get();

        commentService.deleteComment(member.getId(), postId, commentId);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/search/{categoryId}")
    public String searchPost(HttpServletRequest request,
                             @PageableDefault Pageable pageable,
                             @PathVariable("categoryId") Long categoryId,
                             Model model) {
        String search = request.getParameter("search");
        Page<Post> result = postService.findByTitle(search, pageable);

        Category category = categoryService.findOne(categoryId);

        model.addAttribute("boardList", result);
        model.addAttribute("category", category);

        return "post/board";
    }
}
