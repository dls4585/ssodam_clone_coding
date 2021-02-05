package ssodam.ssodam.service;

import ssodam.ssodam.domain.Comment;

public interface CommentService {

//    findOne
//    findAll
//    save

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
}