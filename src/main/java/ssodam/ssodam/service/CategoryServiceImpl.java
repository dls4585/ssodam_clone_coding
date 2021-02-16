package ssodam.ssodam.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.PostRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public Category findOne(Long categoryId) {
        return categoryRepository.getOne(categoryId);
    }
}
