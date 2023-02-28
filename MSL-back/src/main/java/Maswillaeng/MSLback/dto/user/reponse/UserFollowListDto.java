package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;

@Getter
public class UserFollowListDto {
    private Long userId;

    private String nickName;
    private String userImage;

    public UserFollowListDto(User user) {
        this.userId = user.getId();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
    }
}
