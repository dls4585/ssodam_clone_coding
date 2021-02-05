package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.repository.CategoryRepository;
import ssodam.ssodam.repository.MemberRepository;
import ssodam.ssodam.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long post(Long memberId, Long categoryId, String title, String contents) {
        Member member = memberRepository.getOne(memberId);
        Category category = categoryRepository.findOne(categoryId);
        Post post = new Post();
        post.createPost(member, category, title, contents);
        postRepository.save(post);
        // 생성 시간, 업데이트 시간 추가 -> createPost에 추가했음
        return post.getId();
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, Long categoryId, String title, String contents) {
        Post post = postRepository.findOne(postId);
        Category category = categoryRepository.findOne(categoryId);
        post.setCategory(category);
        post.setTitle(title);
        post.setContents(contents);
        // 업데이트 시간 추가
        post.setUpdateDate(LocalDateTime.now());

        return post.getId();
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByMember(Member member) {
        return postRepository.findByMember(member);
    }

    @Override
    public List<Post> findByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}