package ssodam.ssodam.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberForm;

import java.util.Optional;


public interface MemberService extends UserDetailsService {
    public Long createMember(MemberForm form);
    Optional<Member> findByUsername(String username);
}

