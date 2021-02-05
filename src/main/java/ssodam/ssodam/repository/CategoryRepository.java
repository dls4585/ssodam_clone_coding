package ssodam.ssodam.repository;

import ssodam.ssodam.domain.Category;

import java.util.List;

public interface CategoryRepository {
    Category findOne(Long id);
    public List<Category> findAll();
    public Category findByName(String categoryName);
    // 관리자 권한으로 생성 삭제 해야함 -> 논의 필요(위의 save도)
    public Long save(Category category);
}