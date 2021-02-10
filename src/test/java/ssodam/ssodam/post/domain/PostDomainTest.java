package ssodam.ssodam.post.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class PostDomainTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Test

    public void 게시글검증() throws Exception {
        // given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setUsername("dd");
        member2.setUsername("ddd");
        member1.setPassword("dddd");
        member2.setPassword("dddddd");

        Category category = new Category();
        category.setName("anonymous");

        memberRepository.save(member1);
        memberRepository.save(member2);
        categoryRepository.save(category);

        Post post = new Post();
        post.createPost(member1, category, "test", "this is test");



        // when
        postRepository.save(post);
        Post findPost = postRepository.getOne(post.getId());

        // then
        System.out.println(findPost.getId());
        System.out.println(post.getId());
        System.out.println(member1.getPosts());

        assertThat(findPost).isEqualTo(post);
        assertThat(post.getId()).isEqualTo(findPost.getId());
        assertThat(member1.getPosts().size()).isEqualTo(1);
    }
}
