package ssodam.ssodam.domain;

import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostForm {

    private String title;
    private String contents;
    private Category category;
    private Member member;

    public Post toEntity() {
        Post post = Post.builder()
                .title(title)
                .contents(contents)
                .category(category)
                .member(member)
                .build();
        return post;
    }
}
