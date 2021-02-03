package ssodam.clone.service;

import ssodam.clone.domain.Post;

import java.util.List;

public interface PostService {
    public Long post(Long memberId, Long categoryId, String title, String contents);
    public Long updatePost(Long postId, Long categoryId, String title, String contents);
    public void deletePost(Long postId);
    public List<Post> findAll();
    public List<Post> findByMember(Member member);
    public Post findOne(Long postId);
}
