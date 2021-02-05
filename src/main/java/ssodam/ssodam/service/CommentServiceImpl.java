package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CommentRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor        // final로 선언된 field들의 생성자를 만들어줌
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /* 댓글 생성 */
    @Override
    public Comment writeComment(Long memberId, Long postId, String content) {
        //엔티티 조회
        Member member = memberRepository.getOne(memberId);
        Post post = postRepository.findOne(postId);

        //작성 유무 판단
        validateComment(content);

        //댓글 생성
        Comment comment = Comment.createComment(post, member, content);

        //관계 설정
        member.getComments().add(comment);
        post.getComments().add(comment);

        //댓글 저장
        commentRepository.save(comment);    // id 생성->persist
        return comment;
    }

    /* 대댓글 생성 */
    @Override
    public Comment writeSubcomment(Long memberId, Long superCommentId, String content) {
        //엔티티 조회
        Comment superComment = commentRepository.getOne(superCommentId);
        Post post = postRepository.findOne(superComment.getPost().getId());
        Member member = memberRepository.getOne(memberId);

        //대댓글 생성
        Comment subComment = Comment.createComment(post, member, content);

        //관계 설정
        subComment.setSuperComment(superComment);
        superComment.getSubComments().add(subComment);

        //대댓글 저장
        commentRepository.save(subComment);

        return subComment;
    }

    /* 댓글이 등록 가능한지 판단 */
    private void validateComment(String content) {
        if (content.length() == 0) {
            throw new IllegalStateException("내용을 입력하세요.");
        }
    }

    /* 댓글 수정 */
    @Override
    public Comment updateComment(Long memberId, Long commentId, String newContent) {
        //엔티티 조회
        Comment comment = commentRepository.getOne(commentId);
        Member member = memberRepository.getOne(memberId);

        //권한 판단
        validAuthorityComment(member, comment);

        //댓글 수정
        comment.setContent(newContent);
        commentRepository.save(comment);    // merge

        return comment;
    }


    /* 댓글 삭제 */
    @Override
    public void deleteComment(Long memberId, Long postId, Long commentId) {
        //엔티티 조회
        Comment delComment = commentRepository.getOne(commentId);
        Member member = memberRepository.getOne(memberId);
        Post post = postRepository.findOne(postId);

        //권한 판단
        validAuthorityComment(member, delComment);

        while(delComment!=null){
            Comment finalDelComment = delComment;   // removeIf를 사용하기 위해 final 변수로 선언

            //하위 댓글이 존재하지 않을 경우
            if(delComment.getSubComments().size()==0){
                Comment superComment = delComment.getSuperComment();    //상위 댓글

                //상위 댓글이 존재하지 않을 경우
                if(superComment==null){
                    post.getComments()
                            .removeIf(targetComment -> targetComment.equals(finalDelComment));
                    member.getComments()
                            .removeIf(targetComment -> targetComment.equals(finalDelComment));
                    commentRepository.delete(delComment);
                    break;
                }

                //상위 댓글의 대댓글 목록에서 지움
                superComment.getSubComments()
                        .removeIf(targetComment -> targetComment.equals(finalDelComment));
                post.getComments()
                        .removeIf(targetComment -> targetComment.equals(finalDelComment));
                member.getComments()
                        .removeIf(targetComment -> targetComment.equals(finalDelComment));
                commentRepository.delete(delComment);

                //superComment가 삭제된 상태였고
                //delComment를 지움으로써 더 이상 하위 댓글이 없다면 superComment도 삭제한다.
                if(superComment.getSubComments().size()==0 && superComment.getIsValid()==false)
                    delComment = superComment;
                else
                    break;
            }
            //하위 댓글이 존재할 경우
            else{
                //내용과 상태를 바꿔준다.
                delComment.setContent("삭제된 댓글입니다.");
                delComment.setIsValid(false);
            }
        }
    }

    private void validAuthorityComment(Member member, Comment comment) {
        if (comment.getMember().getId() != member.getId()) {
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }
    }
}