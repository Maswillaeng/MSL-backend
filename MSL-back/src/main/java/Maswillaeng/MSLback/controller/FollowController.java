package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.follow.response.FollowResponse;
import Maswillaeng.MSLback.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;
    private final Map<String, Object> result = new HashMap<>();

    @PostMapping("/api/{userId}/follow")
    public FollowResponse addFollow(@PathVariable("userId") Long userId, Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        followService.addFollow(myId, userId);
        result.put("userId", userId);
        result.put("myId", myId);
        return new FollowResponse(HttpStatus.OK.value(), "팔로우 완료!", result);
    }

    /**
     * 팔로우 취소 기능
     * @param userId
     * @param authentication
     * @return
     */
    @DeleteMapping("/api/{userId}/follow")
    public FollowResponse unFollow(@PathVariable("userId") Long userId, Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        followService.unFollow(myId, userId);
        result.put("userId", userId);
        result.put("myId", myId);
        return new FollowResponse(HttpStatus.OK.value(), "팔로우 취소 완료!", result);
    }
}
