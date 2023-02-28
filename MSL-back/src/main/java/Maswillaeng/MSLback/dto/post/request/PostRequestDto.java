package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class PostRequestDto {

    @NotBlank
    private String title;

    private String thumbnail;

    @NotNull
    private String content;

   // private String category;

    private List<String> hashTagList = new ArrayList<>();


    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .thumbnail(thumbnail)
                .content(content)
           //     .category(Category.valueOf(category))
                .build();
    }
}
