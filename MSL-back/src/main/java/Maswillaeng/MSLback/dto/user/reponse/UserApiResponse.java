package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;


@Getter
public class UserApiResponse {
    private Long userId;
    private String nickName;
    private String userImage;
    private String email;
    private String introduction;

    private boolean isLoggedIn;


    public UserApiResponse(User user , boolean isLoggedIn) {
        this.userId = user.getId();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
        this.isLoggedIn = isLoggedIn;
    }

    public UserApiResponse(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
