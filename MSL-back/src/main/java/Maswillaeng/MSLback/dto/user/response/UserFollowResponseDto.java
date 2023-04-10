package Maswillaeng.MSLback.dto.user.response;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserFollowResponseDto {
    private Long userId;

    private String nickName;
    private String userImage;

    public UserFollowResponseDto(User user) {
        this.userId = user.getId();
        this.nickName = user.getNickname();
        this.userImage = user.getUserImage();
    }
}
