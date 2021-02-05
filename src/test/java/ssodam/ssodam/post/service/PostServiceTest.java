package ssodam.ssodam.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;
import ssodam.ssodam.service.PostService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 포스트() throws Exception {
        // given
        Member member = new Member();
        Category category = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category.setName("Anony");
        categoryRepository.save(category);

        Long memberId = member.getId();
        Long categoryId = category.getId();
        // when
        Long post = postService.post(memberId, categoryId, "test", "this is test");

        // then
        assertThat(post).isNotNull();
    }

    @Test
    public void 아이디로찾기() throws Exception {
        // given
        Member member = new Member();
        Category category = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category.setName("Anony");
        categoryRepository.save(category);

        Long memberId = member.getId();
        Long categoryId = category.getId();
        Long postId = postService.post(memberId, categoryId, "test", "this is test");

        // when
        Post findPost = postService.findOne(postId);

        // then
        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findPost.getMember().getId()).isEqualTo(memberId);
        assertThat(findPost.getCategory().getId()).isEqualTo(categoryId);
        assertThat(findPost.getTitle()).isEqualTo("test");
        assertThat(findPost.getContents()).isEqualTo("this is test");
    }

    @Test
    public void 멤버로찾기() throws Exception {
        // given
        Member member = new Member();
        Category category = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category.setName("Anony");
        categoryRepository.save(category);

        Long memberId = member.getId();
        Long categoryId = category.getId();

        // when
        postService.post(memberId, categoryId, "test", "this is test");
        postService.post(memberId,categoryId,"test2","this is 2nd test");
        Member findMember = memberRepository.getOne(memberId);
        List<Post> posts = postService.findByMember(findMember);

        // then
        assertThat(posts.size()).isEqualTo(2);
        for (Post post: posts){
            System.out.println("post.getTitle() = " + post.getTitle());
            assertThat(post.getMember().getId()).isEqualTo(memberId);
        }
    }

    @Test
    public void 모두찾기() throws Exception {
        // given
        Member member = new Member();
        Category category = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category.setName("Anony");
        categoryRepository.save(category);

        Long memberId = member.getId();
        Long categoryId = category.getId();

        // when
        postService.post(memberId, categoryId, "test", "this is test");
        postService.post(memberId,categoryId,"test2","this is 2nd test");
        postService.post(memberId, categoryId, "test3", "this is 3nd test");

        List<Post> all = postService.findAll();

        // then
        assertThat(all.size()).isEqualTo(3);

    }

    @Test
    public void 카테고리로찾기() throws Exception {
        Member member = new Member();
        Category category = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category.setName("Anony");
        categoryRepository.save(category);

        Long memberId = member.getId();
        Long categoryId = category.getId();

        // when
        postService.post(memberId, categoryId, "test", "this is test");
        postService.post(memberId,categoryId,"test2","this is 2nd test");
        postService.post(memberId, categoryId, "test3", "this is 3nd test");
        Category findCategory = categoryRepository.getOne(categoryId);
        List<Post> byCategory = postService.findByCategory(findCategory);
        // then
        assertThat(byCategory.size()).isEqualTo(3);

    }

    @Test
    public void 업데이트() throws Exception {
        // given
        Member member = new Member();
        Category category1 = new Category();
        Category category2 = new Category();
        member.setUsername("dd");
        member.setPassword("dddd");
        memberRepository.save(member);
        category1.setName("Anony");
        category2.setName("Anony2");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Long memberId = member.getId();
        Long categoryId = category1.getId();
        Long category2Id = category2.getId();

        // when
        Long postId = postService.post(memberId, categoryId, "test", "this is test");
        Long updated = postService.updatePost(postId, category2Id, "test2", "this is updated test");

        // then
        assertThat(postId).isEqualTo(updated);
        assertThat(postService.findOne(updated).getCategory().getId()).isEqualTo(category2Id);
        assertThat(postService.findOne(updated).getCategory()).isEqualTo(category2);
        assertThat(postService.findOne(updated).getTitle()).isEqualTo("test2");
        assertThat(postService.findOne(updated).getContents()).isEqualTo("this is updated test");

    }

    @Test
    public void 삭제() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("p");
        member.setPassword("d");
        memberRepository.save(member);

        Category category = new Category();
        category.setName("dd");
        categoryRepository.save(category);

        Long postId = postService.post(member.getId(), category.getId(), "title", "contents");

        List<Post> before = postService.findAll();
        System.out.println("before = " + before);
        // when
        postService.deletePost(postId);

        List<Post> after = postService.findAll();
        System.out.println("after = " + after);

        // then
        assertThat(after.size()).isEqualTo(before.size()-1);
        
    }

//    @Test
//    public void 뀨() throws Exception {
//        // given
//        Member member = new Member();
//        member.setPassword("d");
//        member.setUsername("d");
//        memberRepository.save(member);
//
//        List<Member> members = memberRepository.findAll();
//
//        memberRepository.deleteById(member.getId());
//
//        assertThat(memberRepository.findAll().size()).isEqualTo(members.size()-1);
//        // when
//
//        // then
//
//    }

}
