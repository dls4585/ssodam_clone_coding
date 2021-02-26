package ssodam.ssodam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Likes {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private Long postId;

    private LikeStatus status;

    public static Likes createLike(Long memberId, Long postId, LikeStatus status) {
        Likes likes = new Likes();
        likes.setMemberId(memberId);
        likes.setPostId(postId);
        likes.setStatus(status);

        return likes;
    }
}

