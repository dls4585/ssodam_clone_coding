package ssodam.clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.clone.domain.Category;
import ssodam.clone.domain.Post;
import ssodam.clone.repository.CategoryRepository;
import ssodam.clone.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    private MemberRepository memberRepository;
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long post(Long memberId, Long categoryId, String title, String contents) {
//        Member member = memberRepository.findOne(memberId);
        Category category = categoryRepository.findOne(categoryId);
        Post post = new Post();
        post.createPost(member, category, title, contents);
        postRepository.save(post);
        // 생성 시간, 업데이트 시간 추가
        return post.getId();
    }

    @Override
    @Transactional
    public void updatePost(Long postId, Long categoryId, String title, String contents) {
        Post post = postRepository.findOne(postId);
        Category category = categoryRepository.findOne(categoryId);
        post.setCategory(category);
        post.setTitle(title);
        post.setContents(contents);
        // 업데이트 시간 추가
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
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}
