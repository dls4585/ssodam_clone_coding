package ssodam.ssodam.service;

import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;

import java.util.List;

public interface PostService {
    public Long post(Long memberId, Long categoryId, String title, String contents);
    public Long updatePost(Long postId, Long categoryId, String title, String contents);
    public void deletePost(Long postId);
    public List<Post> findAll();
    public List<Post> findByMember(Member member);
    public List<Post> findByCategory(Category category);
    public Post findOne(Long postId);
}
