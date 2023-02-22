package com.maswilaeng.controller;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.request.PostUpdateDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import com.maswilaeng.service.PostService;
import com.maswilaeng.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    /* CREATE */
    @PostMapping("/post")
    public ResponseEntity savePost(@RequestBody PostRequestDto dto, Long userId) {
        postService.save(userId, dto);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }

    /* READ */
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, new PostResponseDto(post)
        ));
    }

    /* UPDATE */
    // updateMapping 이었는지 put이었는지 기억이 안남.
    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable  @RequestBody PostUpdateDto postUpdateDto) throws Exception {
        postService.updatePost(postRepository.findUserIdById(postUpdateDto.getId()),postUpdateDto);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) throws ValidationException {
        postService.delete(UserContext.userData.get().getUserId(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }
}
