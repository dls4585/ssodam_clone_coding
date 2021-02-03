package ssodam.ssodam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Comment;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    /* save & update */
    public void save(Comment comment){
        if(comment.getId() == null)
            em.persist(comment);
        else
            em.merge(comment);
    }

    /* delete */
    public void delete(Comment comment){
        em.remove(comment);
    }

    /* find */
    public Comment findOne(Long id){
        return em.find(Comment.class, id);
    }

    /* find by content */
    public List<Comment> findByContent(String content){
        return em.createQuery("select c from Comment c where c.content = :content", Comment.class)
                .setParameter("content", content)
                .getResultList();
    }
}
