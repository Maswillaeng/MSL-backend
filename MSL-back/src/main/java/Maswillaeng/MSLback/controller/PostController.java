package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostController {

    private final PostService postService;

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/changeFormatImage")
    public String Image(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info(messageBody);

        return "성공";

    }


//    @ValidToken
//    @AuthCheck(role = AuthCheck.Role.USER)
//    @PostMapping("/api/changeFormatImage")
//    public String Image(File file, HttpServletRequest httpServletRequest){
//        System.out.println(file);
//        System.out.println(httpServletRequest);
//        return "성공";
//    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/post")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.registerPost(UserContext.userData.get().getUserId(), requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "게시물이 성공적으로 등록되었습니다."
        ));
    }

    @GetMapping("/api/post")
    public ResponseEntity<?> getPostList(@RequestParam(required = false) Category category) {

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, postService.getPostList(category)));
    }

    @ValidToken
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, postService.getPostById(postId)
        ));
    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) throws Exception {

        postService.updatePost(UserContext.userData.get().getUserId(), updateDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws ValidationException {
        postService.deletePost(UserContext.userData.get().getUserId(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @GetMapping("/api/post/user")
    public ResponseEntity<?> getUserPostList(@RequestParam Long userId,
                                             @RequestParam(required = false) String category,
                                             @RequestParam int offset) {
        return ResponseEntity.ok().body(ResponseDto.of(
                "유저 게시글 목록 조회에 성공했습니다",
                postService.getUserPostList(userId, category, offset))
        );
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.ADMIN)
    @GetMapping("/api/post/report")
    public ResponseEntity<?> getReportedPostList(@RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                "신고 횟수가 50회 이상인 게시물 조회에 성공했습니다.",
                postService.getReportedPostList(page)));
    }
}
