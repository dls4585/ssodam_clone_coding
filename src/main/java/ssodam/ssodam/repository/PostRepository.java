package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;

import java.util.List;

public interface PostRepository {
    public void save(Post post);
    public Post findOne(Long id);
    public List<Post> findAll();
    public  List<Post> findByMember(Member member);
    public List<Post> findByCategory(Category category);
    public void delete(Long id);
}
