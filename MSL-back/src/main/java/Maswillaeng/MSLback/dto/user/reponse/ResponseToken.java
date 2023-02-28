package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseToken {
    private final String accessToken;
    private final String refreshToken;

    public ResponseToken(String accessToken, String type) {
        this.accessToken = accessToken;
        this.refreshToken = type;
    }
}
