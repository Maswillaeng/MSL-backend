package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserApiResponse;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //////////////////////////////// 종속되어있지 않는 user api
    @ValidToken
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserApi() {
        if (UserContext.userData.get() == null) return ResponseEntity.ok().body(new UserApiResponse(false));

        return ResponseEntity.ok().body(userService.getUserApi(UserContext.userData.get().getUserId()));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/userInfo")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok().body(
                userService.getUser(UserContext.userData.get().getUserId()));
    }


    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getPassword() == null && requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userData.get().getUserId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/user")
    public ResponseEntity<Object> userWithDraw() {
        userService.userWithdraw(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().build();
    }
}
