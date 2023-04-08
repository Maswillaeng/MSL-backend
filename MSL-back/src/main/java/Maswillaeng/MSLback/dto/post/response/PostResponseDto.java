package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long Id;
    private String nickname;
    private String userImage;
    private String thumbnail;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdDate;
    private List<CommentResponseDto> commentList;

    public PostResponseDto(Post post) {
        this.Id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.userImage = post.getUser().getUserImage();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.createdDate = post.getCreatedDate();
        this.commentList = post.getCommentList().stream()
                .filter(comment -> comment.getParent() == null)
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}

