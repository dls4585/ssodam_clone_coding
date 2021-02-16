package ssodam.ssodam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.service.PostService;

@Controller
public class BoardController {
    @Autowired private PostService postService;

    @GetMapping("board")
    public String board(@RequestParam("categoryId") String categoryId,
                        @RequestParam("contentId") Long contentId,
                        Model model){

        Post post = postService.findOne(contentId);

        model.addAttribute("post", post);
        return "board";
    }
}