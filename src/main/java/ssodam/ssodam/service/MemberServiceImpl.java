
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new Member(username, memberEntity.getPassword(), memberEntity.getEmail(), authorities);
    }

    @Transactional
    @Override
    public Long updateInfo(String username, String newName, String email) {
        Member member  = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));

        member.setUsername(newName);
        member.setEmail(email);
        return member.getId();
    }

    @Override
    @Transactional
    public Long updatePassword(String username, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.get();
        member.setPassword(newPassword);
        return member.getId();
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
                        .createDate(LocalDateTime.now())
                        .email(form.getEmail())
                        .build()).getId();
    }

    @Override
    @Transactional
    public void deleteMember(String username) {
        Member member = memberRepository.findByUsername(username).get();
        memberRepository.delete(member);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> findAll() { return memberRepository.findAll(); }
    
    @Override
    public Optional<Member> findByEmail(String email){ return memberRepository.findByEmail(email);}

    @Override
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
        return;
    }

    @Override
    public Member findOne(Long memberId) {
        return memberRepository.getOne(memberId);
    }
}

