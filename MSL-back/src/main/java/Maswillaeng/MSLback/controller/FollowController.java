package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.FollowService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @ValidToken
    @PostMapping("/api/following/{userId}")
    public ResponseEntity<?> following(@PathVariable Long userId){
        followService.following(UserContext.userData.get().getUserId(),userId);
        return ResponseEntity.ok().body(ResponseDto.of("구독이 완료되었습니다."));
    }
    @ValidToken
    @GetMapping("/api/followingList/{userId}")
    public ResponseEntity<?> followingList(@PathVariable Long userId){

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,followService.followingList(userId))); // TODO : responseDto에 안담은 이유가 있는지?
    }

    @ValidToken
    @GetMapping("/api/followerList/{userId}")
    public ResponseEntity<?> followerList(@PathVariable Long userId){
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,followService.followerList(userId)));
    }

    @ValidToken
    @GetMapping("/api/newpid")
    public ResponseEntity<?> followingPostList(){
        followService.followingPostList(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,followService.followingPostList(UserContext.userData.get().getUserId())));
    }

}