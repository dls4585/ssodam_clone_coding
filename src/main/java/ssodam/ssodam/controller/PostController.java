package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.domain.PostForm;
import ssodam.ssodam.service.CategoryService;
import ssodam.ssodam.service.PostService;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final MemberService memberService;
    private final PostService postService;
    private final CategoryService categoryService;
    @GetMapping("/board/{category}")
    public String postView(@PathVariable("category") Long categoryId, @PageableDefault Pageable pageable, Model model) {
        Category category = categoryService.findOne(categoryId);
        Page<Post> boardList = postService.getPostListByCategory(category, pageable);
        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryName", categoryId);
        return "board";
    }

    @GetMapping("/write/{category}")
    public String post(@PathVariable("category") Long categoryId, Model model) {
        Category category = categoryService.findOne(categoryId);
        model.addAttribute("categoryName", categoryId);
        return "write";
    }

    @PostMapping("/write/{category}")
    public String writePost(@PathVariable("category") Long categoryId, PostForm postForm){
        Category category = categoryService.findOne(categoryId);
        postForm.setCategory(category);
        postService.post(postForm);

        return "redirect:/board/{category}";
    }
}
