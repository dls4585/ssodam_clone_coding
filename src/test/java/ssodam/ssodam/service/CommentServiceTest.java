package ssodam.ssodam.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.repository.CommentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentService commentService;


    @Test
    void writeComment() {
        Member member = memberRepository.findOne(1L);
        Post post = postRepository.findOne(1L);
        String content = "comment1";

        Comment comment = commentService.writeComment(post.getId(), member.getId(), content);
        Assertions.assertThat(comment.getContent()).isEqualTo("comment1");
    }

    @Test
    private void validateComment(String content) {

    }

    @Test
    public void updateComment(Long memberId, Long commentId, String newContent) {

    }

    @Test
    private void validateUpdateComment(Member member, Comment comment) {

    }

    @Test
    public void deleteComment(Long commentId) {

    }

    /* 댓글 비추천 */
    public void dislikeComment(Long commentId) {
        //엔티티 조회
        Comment comment = commentRepository.findOne(commentId);

        //댓글 비추천
        Comment.dislikeComment(comment);
    }
}