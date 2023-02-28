package Maswillaeng.MSLback.dto.comment.response;

import Maswillaeng.MSLback.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long userId;
    private String nickName;
    private String userImage;
    private String content;
    private LocalDateTime createAt;
    private boolean isLiked;
    private int like;

    public CommentResponseDto(Comment comment, int likeCnt, Long userId) {
        commentId = comment.getId();
        content = comment.getContent();
        createAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
        this.isLiked = comment.getCommentLikeList().stream().anyMatch(commentLike ->
                commentLike.getUser().getId().equals(userId));
        nickName = comment.getUser().getNickName();
        userImage = comment.getUser().getUserImage();
        this.like = likeCnt;
    }

    public CommentResponseDto(Comment comment, int likeCnt) {
        commentId = comment.getId();
        content = comment.getContent();
        createAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
        this.isLiked = false;
        nickName = comment.getUser().getNickName();
        userImage = comment.getUser().getUserImage();
        this.like = likeCnt;
    }
}
