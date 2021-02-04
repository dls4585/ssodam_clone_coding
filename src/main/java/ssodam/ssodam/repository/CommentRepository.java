package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
