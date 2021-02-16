package ssodam.ssodam.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.CommentRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @Test
    @Rollback
    void writeComment() {
        //given
        Member member = new Member();
        Post post = new Post();
        Category category = new Category();
        category.setName("test cate");
        member.setUsername("t");
        member.setPassword("tel");
        memberRepository.save(member);
        categoryRepository.save(category);
        post.createPost(member, category, "test title", "test content");
        postRepository.save(post);


        //when
        Comment comment = commentService.writeComment(member.getId(), post.getId(), "test comment");

        System.out.println("comment.getContent() = " + comment.getContent());
        System.out.println("post.getComments() = " + post.getComments());
        System.out.println("member.getComments() = " + member.getComments());

        //then
        assertThat(member.getComments().get(0).getContent()).isEqualTo("test comment");
        assertThat(member.getComments().size()).isEqualTo(1L);
    }

    @Test
    void writeReComment() {
        //given
        Member member = new Member();
        Post post = new Post();
        member.setUsername("testuser");
        member.setPassword("testpw");
        memberRepository.save(member);
        postRepository.save(post);
        Comment comment = commentService.writeComment(member.getId(), post.getId(), "test comment");
        commentRepository.save(comment);

        //when
        Comment recomment = commentService.writeSubcomment(member.getId(), comment.getId(), "test recomment");

        //then
        assertThat(comment.getSubComments().size()).isEqualTo(1);
        assertThat(recomment.getSuperComment().getId()).isEqualTo(comment.getId());
        assertThat(post.getComments().size()).isEqualTo(2);
        assertThat(member.getComments().size()).isEqualTo(2);
    }

    @Test
    void updateComment() {
        //given
        Member member = new Member();
        Post post = new Post();
        member.setUsername("testuser");
        member.setPassword("testpw");
        memberRepository.save(member);
        postRepository.save(post);
        Comment comment = commentService.writeComment(member.getId(), post.getId(), "test comment");
        commentRepository.save(comment);

        //when
        commentService.updateComment(member.getId(), comment.getId(), "update comment");

        //then
        assertThat(comment.getContent()).isNotEqualTo("test comment");
        assertThat(comment.getContent()).isEqualTo("update comment");
    }

    @Test
    void deleteComment(){
        //given
        Member member = new Member();
        Post post = new Post();
        member.setUsername("testuser");
        member.setPassword("testpw");
        memberRepository.save(member);
        postRepository.save(post);
        Comment comment = commentService.writeComment(member.getId(), post.getId(), "test comment");
        commentRepository.save(comment);

        //when
        System.out.println("post.getComments().size() = " + post.getComments().size());
        assertThat(post.getComments().size()).isEqualTo(1);
        commentService.deleteComment(member.getId(), post.getId(), comment.getId());

        //then
        assertThat(post.getComments().size()).isEqualTo(0);
    }
}
