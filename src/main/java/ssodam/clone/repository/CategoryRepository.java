package ssodam.clone.repository;

import ssodam.clone.domain.Category;
import ssodam.clone.domain.Post;

import java.util.List;

public interface CategoryRepository {
    public Long save(Category category);
    public Category findOne(Long id);
    public List<Category> findAll();

    // 관리자 권한으로 생성 삭제 해야함 -> 논의 필요(위의 save도)
}
