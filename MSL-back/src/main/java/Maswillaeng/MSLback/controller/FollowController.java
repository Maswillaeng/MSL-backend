package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
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

    @PostMapping("/{toUserId}")
    public ResponseEntity saveFollow(@AuthenticationPrincipal Long userId, @PathVariable Long toUserId){

        followService.saveFollow(userId, toUserId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity unFollow(@AuthenticationPrincipal Long userId, @PathVariable Long toUserId){

        followService.deleteFollow(userId, toUserId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserFollowResponseDto>> findFollowingListByUserId(@PathVariable Long userId){

        List<UserFollowResponseDto> followingList = followService.findFollowingListByUserId(userId);

        return ResponseEntity.ok().body(followingList);
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserFollowResponseDto>> findFollowerListByUserId(@PathVariable Long userId){

        List<UserFollowResponseDto> followerList = followService.findFollowerListByUserId(userId);

        return ResponseEntity.ok().body(followerList);
    }

    @GetMapping("/following/nickname/{nickname}")
    public ResponseEntity<List<UserFollowResponseDto>> findFollowingListByNickname(@PathVariable String nickname){

        List<UserFollowResponseDto> followingList = followService.findFollowingListByNickname(nickname);

        return ResponseEntity.ok().body(followingList);
    }
    @GetMapping("/follower/nickname/{nickname}")
    public ResponseEntity<List<UserFollowResponseDto>> findFollowerListByNickname(@PathVariable String nickname){

        List<UserFollowResponseDto> followerList = followService.findFollowerListByNickname(nickname);

        return ResponseEntity.ok().body(followerList);
    }
}
