package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/api/like/{postId}")
    public ResponseEntity<Object> saveLike(@AuthenticationPrincipal Long userId,@PathVariable Long postId) {
        likeService.saveLike(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/like/{postId}")
    public ResponseEntity<Object> deleteLike(@AuthenticationPrincipal Long userId, @PathVariable Long postId)  {
        likeService.deleteLike(userId, postId);
        return ResponseEntity.ok().build();
    }

    //    @PostMapping("/api/like/{postId}")
    //    public void pushLike(@AuthenticationPrincipal Long userId, @PathVariable Long postId) {
    //        likeService.isLiked(userId, postId);
    //    }
}
