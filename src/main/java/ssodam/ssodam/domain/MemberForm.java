package ssodam.ssodam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberForm {

    private String username;
    private String password;
    private String email;

    @Builder
    public MemberForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
