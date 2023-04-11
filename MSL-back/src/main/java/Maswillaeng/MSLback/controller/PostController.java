package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.response.PostResponseDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity savePost(@AuthenticationPrincipal Long userId, @RequestBody PostsSaveRequestDto requestDto) {
        Long postId = postService.savePost(userId, requestDto);
        return ResponseEntity.ok().body(postId);
    }

    @GetMapping("/posts/{page}")
    public ResponseEntity<?> getAllPosts(@PathVariable int page) {
        Page<PostListResponseDto> postList = postService.getAllPosts(page);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        PostResponseDto responseDto = new PostResponseDto(post);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/posts/{userId}/{page}")
    public ResponseEntity<?> findPostsByUserId(@PathVariable Long userId, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByUserId(userId, page);

        return ResponseEntity.ok(postList);
    }

    @GetMapping("/posts/nickname/{nickname}/{page}")
    public ResponseEntity<?> findPostsByNickname(@PathVariable String nickname, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByNickname(nickname, page);

        return ResponseEntity.ok(postList);
    }

    @GetMapping("/posts/category/{category}/{page}")
    public ResponseEntity<?> findPostsByCategory(@PathVariable String category, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByCategory(category, page);

        return ResponseEntity.ok(postList);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto requestDto) {
        postService.updatePost(postId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> userImageUpdate(@RequestParam("photo") MultipartFile imageFile) throws IOException {

        return ResponseEntity.ok().body(postService.uploadImage(imageFile));
    }
}

