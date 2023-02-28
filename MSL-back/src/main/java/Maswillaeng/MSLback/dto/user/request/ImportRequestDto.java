package Maswillaeng.MSLback.dto.user.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImportRequestDto {
    private String key;
    private String secret;
}
