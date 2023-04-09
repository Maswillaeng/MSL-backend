package Maswillaeng.MSLback.dto.user.response;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String nickname;
    private String userImage;
    private String introduction;


    @Builder
    public UserResponseDto(User user){
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
    }
}
