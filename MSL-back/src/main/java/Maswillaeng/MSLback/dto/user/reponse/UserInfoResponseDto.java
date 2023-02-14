package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoResponseDto {

    private String email;

    private String nickName;

    private String userImage;

    private String introduction;

    private boolean isLoggedIn;

    public UserInfoResponseDto(boolean loginState) {
        this.isLoggedIn = loginState;
    }

    public static UserInfoResponseDto of (boolean loginState) {
        return new UserInfoResponseDto(loginState);
    }

    public static UserInfoResponseDto of(User user, boolean loginState) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getNickName(),
                user.getUserImage(),
                user.getIntroduction(),
                loginState);
    }
}
