package ssodam.ssodam.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private Long postId;

    private LikeStatus status;

    public static Like createLike(Long memberId, Long postId, LikeStatus status) {
        Like like = new Like();
        like.setMemberId(memberId);
        like.setPostId(postId);
        like.setStatus(status);

        return like;
    }
}

