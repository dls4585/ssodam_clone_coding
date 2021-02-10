
package ssodam.ssodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssodam.ssodam.domain.Member;
import ssodam.ssodam.domain.MemberForm;
import ssodam.ssodam.domain.MemberRole;
import ssodam.ssodam.repository.MemberRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member memberEntity = member.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new Member(username, memberEntity.getPassword());
    }

    @Transactional
    @Override
    public Long createMember(MemberForm form) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        return memberRepository.save(
                Member.builder()
                        .username(form.getUsername())
                        .password(form.getPassword())
                        .build()).getId();
    }
}

