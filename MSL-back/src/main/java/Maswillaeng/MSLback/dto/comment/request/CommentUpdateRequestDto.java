package Maswillaeng.MSLback.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentUpdateRequestDto {
    private Long commentId;
    private String content;
}
