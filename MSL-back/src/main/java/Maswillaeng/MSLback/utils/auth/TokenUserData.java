package Maswillaeng.MSLback.utils.auth;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenUserData {
    private Long userId;
    private String userRole;

    public TokenUserData(Claims claims) {
        this.userId = Long.parseLong(String.valueOf(claims.get("userId")));
        this.userRole = (String) claims.get("role");
    }

}
