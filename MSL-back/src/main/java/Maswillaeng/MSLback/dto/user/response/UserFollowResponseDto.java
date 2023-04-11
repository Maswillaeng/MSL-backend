package Maswillaeng.MSLback.dto.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserFollowResponseDto {
    private Long userId;
    private Long otherUserId;

    private boolean isFollow;

    private int myFollowerCnt;
    private int myFollowingCnt;

    private int otherFollowerCnt;
    private int otherFollowingCnt;

    @Builder
    public UserFollowResponseDto(Long userId, Long otherUserId, boolean isFollow, int myFollowerCnt, int myFollowingCnt, int otherFollowerCnt, int otherFollowingCnt) {
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.isFollow = isFollow;
        this.myFollowerCnt = myFollowerCnt;
        this.myFollowingCnt = myFollowingCnt;
        this.otherFollowerCnt = otherFollowerCnt;
        this.otherFollowingCnt = otherFollowingCnt;
    }

}
