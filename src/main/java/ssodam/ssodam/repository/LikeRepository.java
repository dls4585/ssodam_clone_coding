package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssodam.ssodam.domain.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);
}
