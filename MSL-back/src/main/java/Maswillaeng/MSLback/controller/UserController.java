package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserInfoResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/api-user")
    public ResponseEntity<?> getUserInfo(@CookieValue(name = "ACCESS_TOKEN", required = false)
                                         String accessToken,
                                         HttpServletResponse response) throws IOException {
        UserInfoResponseDto userInfo = authService.validateUserAndGetUserInfo(accessToken, response);
//        if (userInfo == null) {
//            return ResponseEntity.status(302).location(uri)
//        }

        return ResponseEntity.ok().body(ResponseDto.of(
                "유저 정보 조회 성공",
                userInfo
        ));
    }


    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getPassword() == null && requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userData.get().getUserId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/user")
    public ResponseEntity<Object> userWithDraw() {
        userService.userWithdraw(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().build();
    }
}
