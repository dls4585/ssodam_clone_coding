package ssodam.ssodam.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ssodam.ssodam.domain.MemberForm;


public interface MemberService extends UserDetailsService {
    public Long createMember(MemberForm form);

}

