package ssodam.ssodam.service;

import ssodam.ssodam.domain.Category;

import java.util.List;

public interface CategoryService {
    public Long createCategory(Category category);
    public Category findOne(Long categoryId);
    public List<Category> findAll();
}
