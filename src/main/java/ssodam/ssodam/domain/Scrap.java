package ssodam.ssodam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Scrap {
    @Id
    @GeneratedValue
    @Column(name="scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public static Scrap createScrap(Member member, Post post) {
        Scrap scrap = new Scrap();
        scrap.setMember(member);
        scrap.setPost(post);

        return scrap;
    }
}
