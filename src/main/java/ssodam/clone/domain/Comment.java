package ssodam.clone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 도메인 모델 패턴으로 작성하려고 노력
public class Comment {

    @Id @GeneratedValue
    @Column(name ="comment_id")
    private Long id;

    private String content;

    private Long dislike;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //==생성 메서드==//
    public static Comment createComment(Post post, Member member, String content){
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(content);
        comment.setDislike(0L);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        // 멤버가 등록한 댓글
        member.getComments().add(comment);

        // 포스트에 등록된 댓글
        post.getComments().add(comment);
        return comment;
    }

    //==비지니스 로직==//
    /**
     * 댓글 삭제
     */
    public static void deleteComment(Comment comment){
        // 엔티티 조회
        Member member = comment.getMember();
        Post post = comment.getPost();

        // 멤버가 작성한 댓글 목록에서 삭제
        for(Comment del : member.getComments()){
            if(del.equals(comment)){
                member.getComments().remove(del);
                break;
            }
        }

        // 포스트 댓글 목록에서 삭제
        for(Comment del : post.getComments()){
            if(del.equals(comment)){
                post.getComments().remove(del);
                break;
            }
        }
    }

    /**
     * 댓글 수정
     */
    public static void updateComment(Comment comment, String newContent) {
        comment.setContent(newContent);
        comment.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 댓글 비추천
     */
    public static void dislikeComment(Comment comment){
        Long dislike = comment.getDislike();
        dislike--;
        comment.setDislike(dislike);
    }
}
