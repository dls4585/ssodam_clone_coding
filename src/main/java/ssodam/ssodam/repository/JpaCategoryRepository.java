package ssodam.ssodam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCategoryRepository implements CategoryRepository{

    private final EntityManager em;

    @Override
    public Long save(Category category) {
        em.persist(category);
        return category.getId();
    }

    @Override
    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
