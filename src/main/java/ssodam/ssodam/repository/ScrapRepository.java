package ssodam.ssodam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.Post;
import ssodam.ssodam.domain.Scrap;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    public void deleteByPostAndMember(Post post, Member member);
}
