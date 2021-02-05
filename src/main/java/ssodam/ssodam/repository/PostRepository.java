package ssodam.ssodam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Category;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    public List<Post> findByMember(Member member);
    public List<Post> findByCategory(Category category);

}
