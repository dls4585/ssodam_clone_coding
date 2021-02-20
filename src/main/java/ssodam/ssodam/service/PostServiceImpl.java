package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.domain.PostForm;
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
    public Long post(PostForm postForm) {
       return postRepository.save(postForm.toEntity()).getId();
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, Long categoryId, String title, String contents) {
        Post post = postRepository.getOne(postId);
        Category category = categoryRepository.getOne(categoryId);
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
        Post post = postRepository.getOne(postId);
        post.getCategory().getPosts()
                .removeIf(targetPost -> targetPost.equals(post));
        post.getMember().getPosts()
                .removeIf(targetPost -> targetPost.equals(post));
        postRepository.deleteById(postId);
    }


    @Override
    public Post findOne(Long postId) {
        return postRepository.getOne(postId);
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
    public List<Post> findByCategory(Category category){ return postRepository.findByCategory(category); }



    @Override
    public Page<Post> getPostListByCategory(Category category, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return postRepository.findByCategory(category, pageable);
    }

    @Override
    @Transactional
    public void increaseVisit(Post post) {
        int visit = post.getVisit();
        post.setVisit(visit+1);
    }
}