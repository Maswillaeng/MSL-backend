package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;

    private Long userId;
    private String nickName;
    private String userImage;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long likeCnt;
    private Long commentCnt;
    private Long hits;


    public PostResponseDto(Post post, Long likeCnt, Long commentCnt) {
        postId = post.getId();
        userId = post.getUser().getId();
        nickName = post.getUser().getNickName();
        userImage = post.getUser().getUserImage();
        thumbnail = post.getThumbnail();
        title = post.getTitle();
        hits = post.getHits();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }



}
