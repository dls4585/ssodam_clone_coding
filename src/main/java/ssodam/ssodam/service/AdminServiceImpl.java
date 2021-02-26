package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService{

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long createCategory(Category category) {
       return categoryRepository.save(category).getId();
    }

    @Override
    public void blockMember(Long memberId) {

    }

    @Override
    public boolean loginCheck(Long memberId) {
        return false;
    }
}
