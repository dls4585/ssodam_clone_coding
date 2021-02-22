package ssodam.ssodam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.domain.PostForm;

import java.util.List;

public interface PostService {
    public Long post(PostForm postForm);
    public Long updatePost(Long postId, Long categoryId, String title, String contents);
    public Post findOne(Long postId);
    public List<Post> findAll();
    public List<Post> findByMember(Member member);
    List<Post> findByCategory(Category category);
    public void deletePost(Long postId);
    //새로 추가
    public Page<Post> getPostListByCategory(Category category, Pageable pageable);
    public void increaseVisit(Post post);

    // 새로 추가
    public Page<Post> findByTitle(String search, Pageable pageable);
}
