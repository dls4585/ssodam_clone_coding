package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Comment;
import ssodam.ssodam.domain.Post;
import java.util.List;

@Repository //JPARepository를 사용하기 때문에 어노테이션을 적을 필요는 없지만 가독성을 위해 적어둠
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByMember(Member member);

    List<Comment> findByPost(Post post);
}
