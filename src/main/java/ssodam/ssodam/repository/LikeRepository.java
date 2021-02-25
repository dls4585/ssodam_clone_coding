package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssodam.ssodam.domain.Likes;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberIdAndPostId(Long memberId, Long postId);
}
