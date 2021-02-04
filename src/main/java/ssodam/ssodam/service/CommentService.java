package ssodam.ssodam.service;

import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Member;

public interface CommentService {

    public Comment writeComment(Long postId, Long memberId, String content);

    /* 댓글이 등록 가능한지 판단 */
    private void validateComment(String content) {
        if (content.length() == 0) {
            throw new IllegalStateException("내용을 입력하세요.");
        }
    }

    /* 댓글 수정 */
    public Comment updateComment(Long memberId, Long commentId, String newContent);

    /* 댓글 삭제 */
    public void deleteComment(Long memberId, Long postId, Long commentId);

    private void validateUpdateComment(Member member, Comment comment) {
        if(comment.getMember().getId() != member.getId()){
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }
    }

    /* 댓글 비추천 */
    public Long dislikeComment(Long commentId);
}
