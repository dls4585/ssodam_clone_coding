package ssodam.clone.repository;

import ssodam.clone.domain.Category;
import ssodam.clone.domain.Post;

import java.util.List;

public interface PostRepository {
    public void save(Post post);
    public Post findOne(Long id);
    public List<Post> findAll();
    public  List<Post> findByMember(Member member);
    public List<Post> findByCategory(Category category);
    public void delete(Long id);
}
