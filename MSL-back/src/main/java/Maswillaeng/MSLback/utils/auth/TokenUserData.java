package Maswillaeng.MSLback.utils.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenUserData {
    private Long userId;
    private String userRole;
}
