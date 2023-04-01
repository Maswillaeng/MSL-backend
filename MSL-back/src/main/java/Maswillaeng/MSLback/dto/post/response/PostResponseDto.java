package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long Id;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    public PostResponseDto(Post entity) {
        this.Id = entity.getId();
        this.thumbnail = entity.getThumbnail();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
    }
}

