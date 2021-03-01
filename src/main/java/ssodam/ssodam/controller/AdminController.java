package ssodam.ssodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.service.CategoryService;
import ssodam.ssodam.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final CategoryService categoryService;

    @GetMapping("/admin/category/{categoryClass}")
    public String category(@PathVariable("categoryClass") String categoryClass, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categoryClass", categoryClass);
        model.addAttribute("categoryList", categories);
        return "admin/category";
    }

    @GetMapping("admin/member")
    public String member(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("memberList", members);
        return "admin/member";
    }

    @GetMapping("/admin/category/create")
    public String getCreateCategory() {
        return "admin/create";
    }

    @PostMapping("/admin/category/create")
    public String postCreateCategory(Category category) {
        categoryService.createCategory(category);
        return "redirect:/admin/member";
    }

    @DeleteMapping("/admin/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findOne(categoryId);
        String categoryClass = category.getCategoryClass();
        categoryService.deleteCategory(categoryId);
        redirectAttributes.addAttribute("categoryClass", categoryClass);
        return "redirect:/admin/category";
    }


    @DeleteMapping("/admin/member/delete/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        memberService.deleteMember(member);
        return "redirect:/admin/member";
    }
}


