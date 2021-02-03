package ssodam.clone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
