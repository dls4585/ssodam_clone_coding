package ssodam.clone.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.clone.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private EntityManager em;

    /**
     * create
     */
    public void save(Post post) {
        em.persist(post);
    }

    /**
     * read
     */

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public  List<Post> findByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .setParameter("member", member)
                .getResultList();
    }

    /**
     * update
     */



    /**
     * delete
     */
    public void delete(Long id) {
        em.createQuery("delete from Post p where p.id =: id", Post.class)
                .setParameter("id", id);
    }
}
