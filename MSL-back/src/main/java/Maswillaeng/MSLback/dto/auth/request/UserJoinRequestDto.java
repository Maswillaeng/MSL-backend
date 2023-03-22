package Maswillaeng.MSLback.dto.auth.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    public User toEntity(String encryptPw){
        return User.builder()
                .email(email)
                .password(encryptPw)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .build();
    }
}
