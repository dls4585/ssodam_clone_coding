package ssodam.clone.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssodam.clone.domain.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public void delete(Member member){
        em.detach(member);
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
