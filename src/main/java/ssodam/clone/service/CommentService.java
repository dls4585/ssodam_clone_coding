package ssodam.clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssodam.clone.domain.Comment;
import ssodam.clone.domain.Member;
import ssodam.clone.domain.Post;
import ssodam.clone.repository.CommentRepository;
import ssodam.clone.repository.MemberRepository;
import ssodam.clone.repository.PostRepository;

import javax.transaction.Transactional;
import java.net.CookieManager;

@Service
@Transactional
@RequiredArgsConstructor        // final로 선언된 field들의 생성자를 만들어줌
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    /* 댓글 생성 */
    public Long writeComment(Long postId, Long memberId, String content){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Post post = postRepository.findOne(postId);

        //작성 유무 판단
        validateComment(content);

        //댓글 생성
        Comment comment = Comment.createComment(post, member, content);

        //댓글 저장
        commentRepository.save(comment);
        return comment.getId();
    }

    /* 댓글이 등록 가능한지 판단 */
    private void validateComment(String content) {
        if (content.length() == 0) {
            throw new IllegalStateException("내용을 입력하세요.");
        }
    }

    /* 댓글 수정 */
    public void updateComment(Long commentId, String newContent){
        // 엔티티 조회
        Comment comment = commentRepository.findOne(commentId);

        // 댓글 수정
        Comment.updateComment(comment, newContent);
    }

    /* 댓글 삭제 */
    public void deleteComment(Long commentId){
        // 엔티티 조회
        Comment comment = commentRepository.findOne(commentId);

        // 댓글 삭제
        Comment.deleteComment(comment);
    }

    /* 댓글 비추천 */
    public void dislikeComment(Long commentId){
        // 엔티티 조회
        Comment comment = commentRepository.findOne(commentId);

        // 댓글 비추천
        Comment.dislikeComment(comment);
    }
}