package ssodam.ssodam.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    /**
     * create
     */
    @Override
    public void save(Post post) {
        em.persist(post);
    }

    /**
     * read
     */
    @Override
    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public  List<Post> findByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public List<Post> findByCategory(Category category) {
        return em.createQuery("select p from Post p where p.category = :category", Post.class)
                .setParameter("category", category)
                .getResultList();
    }

    /**
     * update
     */



    /**
     * delete
     */
    public void delete(Long id) {
        Post post = findOne(id);
        em.remove(post);
    }
}
