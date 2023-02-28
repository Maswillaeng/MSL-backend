package Maswillaeng.MSLback.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommentRequestDto {
    private Long parentId;
    private String content;
}
