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
public abstract class PostRepositoryImpl implements PostRepository{

    private final EntityManager em;

    /**
     * create
     * @return
     */
    @Override
    public Post save(Post post) {
        em.persist(post);
        return null;
    }

    /**
     * read
     */

    public  List<Post> findByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .setParameter("member", member)
                .getResultList();
    }


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
        Post post = getOne(id);
        List<Post> posts = post.getMember().getPosts();
        posts.remove(post);
//        em.remove(post);
        em.createQuery("delete from Post p where p.id =: id")
                .setParameter("id", id).executeUpdate();
//        post.getMember().getPosts().remove(post);
    }
}
