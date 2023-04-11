package Maswillaeng.MSLback.dto.user.response;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserFollowResponseDto {
    private Long userId;

    private String nickname;
    private String userImage;

    public UserFollowResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.userImage = user.getUserImage();
    }
}
