package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserPostResponseDto {
    private Long postId;
    private String nickName;
    private String ThumbNail;
    private String title;
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public UserPostResponseDto(Post post) {
        postId = post.getId();
        nickName = post.getUser().getNickName();
        ThumbNail = post.getThumbnail();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }
}
