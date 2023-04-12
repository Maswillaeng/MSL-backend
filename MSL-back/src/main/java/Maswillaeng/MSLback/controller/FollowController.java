package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto;
import Maswillaeng.MSLback.dto.user.response.UserFollowResponseDto;
import Maswillaeng.MSLback.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{nickname}")
    public ResponseEntity<UserFollowResponseDto> saveFollow(@AuthenticationPrincipal Long userId, @PathVariable String nickname){

        UserFollowResponseDto responseDto =  followService.saveFollow(userId, nickname);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<UserFollowResponseDto> unFollow(@AuthenticationPrincipal Long userId, @PathVariable String nickname){

        UserFollowResponseDto responseDto =  followService.deleteFollow(userId, nickname);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserFollowListResponseDto>> findFollowingListByUserId(@PathVariable Long userId){

        List<UserFollowListResponseDto> followingList = followService.findFollowingListByUserId(userId);

        return ResponseEntity.ok().body(followingList);
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserFollowListResponseDto>> findFollowerListByUserId(@PathVariable Long userId){

        List<UserFollowListResponseDto> followerList = followService.findFollowerListByUserId(userId);

        return ResponseEntity.ok().body(followerList);
    }

    @GetMapping("/following/nickname/{nickname}")
    public ResponseEntity<List<UserFollowListResponseDto>> findFollowingListByNickname(@PathVariable String nickname){

        List<UserFollowListResponseDto> followingList = followService.findFollowingListByNickname(nickname);

        return ResponseEntity.ok().body(followingList);
    }
    @GetMapping("/follower/nickname/{nickname}")
    public ResponseEntity<List<UserFollowListResponseDto>> findFollowerListByNickname(@PathVariable String nickname){

        List<UserFollowListResponseDto> followerList = followService.findFollowerListByNickname(nickname);

        return ResponseEntity.ok().body(followerList);
    }

/*    @GetMapping("/isFollow/{toUserId}")
    public ResponseEntity<Boolean> isFollow(@AuthenticationPrincipal Long userId, @PathVariable Long toUserId){
        Boolean isFollow = followService.isFollow(userId, toUserId);
        return ResponseEntity.ok().body(isFollow);
    }*/

    @GetMapping("/following/count/{userId}")
    public ResponseEntity<Integer> countFollowing(@PathVariable Long userId){
        Integer count = followService.countFollowing(userId);
        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/follower/count/{userId}")
    public ResponseEntity<Integer> countFollower(@PathVariable Long userId){
        Integer count = followService.countFollower(userId);
        return ResponseEntity.ok().body(count);
    }
}
