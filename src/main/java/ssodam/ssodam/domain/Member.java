
package ssodam.ssodam.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @Column(nullable = false)
    private String password;

    private String email;
}