package Maswillaeng.MSLback.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    @NotEmpty
    private String nickName;
    private String userImage;
    private String introduction;
}