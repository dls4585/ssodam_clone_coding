package ssodam.clone.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.clone.domain.Post;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public void delete(Post post){
        em.detach(post);
    }

    public Post findOne(Long postId){
        return em.find(Post.class, postId);
    }
}
