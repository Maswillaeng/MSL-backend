package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostUpdateDto {

    // 이건 필요 없음
    private Long postId;

    private String thumbnail;

    @NotBlank
    private String title;

    @NotNull
    private String content;

    private Category category;

    private List<String> hashTagList;

}
