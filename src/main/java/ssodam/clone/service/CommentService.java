package ssodam.clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssodam.clone.domain.Comment;
import ssodam.clone.repository.CommentRepository;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor        // final로 선언된 field들의 생성자를 만들어줌
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    /* 댓글 생성 */
    public Comment writeComment(Long postId, Long memberId, String content){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Post post = postRepository.findOne(postId);

        //작성 유무 판단
        validateComment(content);

        //댓글 생성
        Comment comment = Comment.createComment(post, member, content);

        //댓글 저장
        commentRepository.save(comment);    // id 생성->persist
        return comment;
    }

    /* 댓글이 등록 가능한지 판단 */
    private void validateComment(String content) {
        if (content.length() == 0) {
            throw new IllegalStateException("내용을 입력하세요.");
        }
    }

    /* 댓글 수정 */
    public Comment updateComment(Long memberId, Long commentId, String newContent){
        //엔티티 조회
        Comment comment = commentRepository.findOne(commentId);
        Member member = memberRepository.findOne(memberId);

        //권한 판단
        validateUpdateComment(member, comment);

        //댓글 수정
        Comment.updateComment(comment, newContent);
        commentRepository.save(comment);    // merge

        return comment;
    }



    /* 댓글 삭제 */
    public void deleteComment(Long memberId, Long postId, Long commentId){
        //엔티티 조회
        Comment comment = commentRepository.findOne(commentId);
        Member member = memberRepository.findOne(memberId);
        Post post = postRepository.findOne(postId);

        //권한 판단
        validateUpdateComment(member, comment);

        //댓글 삭제
        Comment.deleteComment(comment);
        //회원한테서 삭제 함수 필요    리스트에서 빼주는거
        //포스트에서 삭제 함수 필요    리스트에서 빼주는거

        commentRepository.delete(comment);
    }

    private void validateUpdateComment(Member member, Comment comment) {
        if(comment.getMember().getId() != member.getId()){
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }
    }

    /* 댓글 비추천 */
    public Long dislikeComment(Long commentId){
        //엔티티 조회
        Comment comment = commentRepository.findOne(commentId);

        //댓글 비추천
        Comment.dislikeComment(comment);

        //댓글 업데이트
        commentRepository.save(comment);

        return comment.getDislike();
    }
}