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
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/following")
    public ResponseEntity<?> following(@RequestParam Long userId){
        followService.following(UserContext.userData.get().getUserId(),userId);
        return ResponseEntity.ok().body(ResponseDto.of("구독이 완료되었습니다."));
    }
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/followingList")
    public ResponseEntity<?> followingList(@RequestParam Long userId){

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,followService.followingList(userId))); // TODO : responseDto에 안담은 이유가 있는지?
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/followerList")
    public ResponseEntity<?> followerList(@RequestParam Long userId){
         // TODO : 왜 리턴값이 없는지?
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,followService.followerList(userId)));
    }


}
