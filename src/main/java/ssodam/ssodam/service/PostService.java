package ssodam.ssodam.service;

import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;

import java.util.List;

public interface PostService {
    Long updatePost(Long postId, Long categoryId, String title, String contents);
    Long post(Long memberId, Long categoryId, String title, String contents);
    Post findOne(Long postId);
    List<Post> findAll();
    List<Post> findByMember(Member member);
    List<Post> findByCategory(Category category);
    void deletePost(Long postId);
}
