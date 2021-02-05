package ssodam.ssodam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberForm {

    private String username;
    private String password;

    @Builder
    public MemberForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .build();
    }
}
