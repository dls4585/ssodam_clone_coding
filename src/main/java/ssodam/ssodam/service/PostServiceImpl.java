package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.*;
import ssodam.ssodam.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;

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

    @Override
    public Page<Post> getPostListByTitle(String search, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return postRepository.findByTitleContaining(search, pageable);
    }

    @Override
    public Page<Post> getPostListByTitleInCategory(String search, Category category, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return postRepository.findByTitleContainingAndCategory(search, category, pageable);
    }

    @Override
    public Page<Post> getPostListByMember(Member member, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return postRepository.findByMember(member, pageable);
    }

    @Override
    @Transactional
    public void increaseLike(Post post, Member member) {
        int likes = post.getLikes();
        post.setLikes(likes+1);

        Optional<Likes> optional = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
        if(optional.isPresent()) {
            Likes like = optional.get();
            if(like.getStatus() == LikeStatus.DISLIKE) {
                like.setStatus(LikeStatus.LIKE);
            }
            else {
                return;
            }
        } else {
            Likes like = Likes.createLike(member.getId(), post.getId(), LikeStatus.LIKE);
            likeRepository.save(like);
        }


    }

    @Override
    @Transactional
    public void decreaseLike(Post post, Member member) {
        int likes = post.getLikes();
        post.setLikes(likes-1);

        Optional<Likes> optional = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
        if(optional.isPresent()) {
            Likes like = optional.get();
            if(like.getStatus() == LikeStatus.LIKE) {
                like.setStatus(LikeStatus.DISLIKE);
            }
            else {
                return;
            }
        } else {
            Likes like = Likes.createLike(member.getId(), post.getId(), LikeStatus.DISLIKE);
            likeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void scrapPost(Post post, Member member) {
        Scrap scrap = Scrap.createScrap(member, post);

        post.getScrappedBy().add(scrap);
        member.getScraps().add(scrap);
    }

    @Override
    @Transactional
    public void scrapCancel(Post post, Member member) {
        post.getScrappedBy().removeIf(m -> m.getMember().equals(member));
        member.getScraps().removeIf(p -> p.getPost().equals(post));
        scrapRepository.deleteByPostAndMember(post, member);
    }
}