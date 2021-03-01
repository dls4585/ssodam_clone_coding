package ssodam.ssodam.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long createCategory(Category category) {
        return categoryRepository.save(category).getId();
    }

    public Category findOne(Long categoryId) {
        return categoryRepository.getOne(categoryId);
    }

    public List<Category> findAll() { return categoryRepository.findAll(); }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return;
    }

}
