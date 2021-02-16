package ssodam.ssodam.service;

import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Post;

import java.util.List;

public interface CommentService {
    /**
     * 댓글 생성
     */
    Comment writeComment(Long memberId, Long postId, String content);

    /**
     * 대댓글 생성
     */
    Comment writeSubcomment(Long memberId, Long superCommentId, String content);

    /**
     * 댓글 수정
     */
    Comment updateComment(Long memberId, Long commentId, String newContent);

    /**
     * 댓글 삭제
     */
    void deleteComment(Long memberId, Long postId, Long commentId);

    /**
     * 댓글 찾기
     */
    Comment findOne(Long commentId);

    /**
     * 모든 댓글 찾기
     */
    List<Comment> findAll();

    /**
     * 멤버 댓글 찾기
     */
    List<Comment> findByMember(Member member);

    /**
     * 포스트의 댓글 찾기
     */
    List<Comment> findByPost(Post post);
}