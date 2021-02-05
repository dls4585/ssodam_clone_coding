package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberForm;


public interface MemberService extends UserDetailsService {
    public Long createMember(MemberForm form);

}

