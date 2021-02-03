package ssodam.ssodam.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

// 수정 필요 (연관관계 없는 걸로)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // 우선 다대일로 설정, 후에 논의 필요
    @Column(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob // 검색해보기
    private String contents;

    @ColumnDefault("0")
    private int like;
    @ColumnDefault("0")
    private int dislike;
    @ColumnDefault("0")
    private int scrap;
    @ColumnDefault("0")
    private int visit;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // == 생성 메서드 == //

    public void createPost(Member member, Category category, String title, String contents) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.contents = contents;
    }

}
