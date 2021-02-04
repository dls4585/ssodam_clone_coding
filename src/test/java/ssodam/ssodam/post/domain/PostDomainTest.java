package ssodam.ssodam.post.domain;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
@SpringBootTest
public class PostDomainTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Test
    @Transactional
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
        Post findPost = postRepository.findOne(post.getId());

        // then
        assertThat(findPost).isEqualTo(post);
        System.out.println(findPost.getId());
        assertThat(findPost.getId()).isEqualTo(1L);
        assertThat(post.getId()).isEqualTo(1L);

    }
}
