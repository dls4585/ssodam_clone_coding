package ssodam.ssodam.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberForm;

import java.util.List;
import java.util.Optional;


public interface MemberService extends UserDetailsService {
    Long updateInfo(String username, String newName, String email);
    Long updatePassword(String username, String newPassword);
    Long createMember(MemberForm form);
    void deleteMember(String username);
    Optional<Member> findByUsername(String username);
    List<Member> findAll();
    Optional<Member> findByEmail(String email);
    public void deleteMember(Member member);
    public Member findOne(Long memberId);

}

