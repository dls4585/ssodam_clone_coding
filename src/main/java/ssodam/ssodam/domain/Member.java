
package ssodam.ssodam.domain;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private final List<Post> posts = new ArrayList<>();

    @Column(nullable = false)
    private String password;

    //private String email;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private final List<Comment> comments = new ArrayList<>();
}