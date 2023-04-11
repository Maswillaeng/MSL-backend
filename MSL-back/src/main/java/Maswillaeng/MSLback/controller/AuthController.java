package Maswillaeng.MSLback.controller;


import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.dto.auth.request.UserPasswordCheckRequestDto;
import Maswillaeng.MSLback.dto.auth.response.TokenResponseDto;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.auth.request.UserLoginRequestDto;
import Maswillaeng.MSLback.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign")
    public ResponseEntity join(@RequestBody UserJoinRequestDto requestDto) throws Exception {
        if (authService.joinDuplicate(requestDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(requestDto);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto requestDto) throws Exception {
        TokenResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        authService.logout(userId);
        return ResponseEntity.ok().build();
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity reissueToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        TokenResponseDto responseDto = authService.reissueToken(refreshToken);

        return ResponseEntity.ok().body(responseDto); //RTR 선택
    }

    @PostMapping("/duplicate/email")
    public ResponseEntity emailDuplicate(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (authService.emailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/duplicate/nickname")
    public ResponseEntity nicknameDuplicate(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");

        if (authService.nicknameDuplicate(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/password")
    public ResponseEntity<?> checkPassword(@AuthenticationPrincipal Long userId, @RequestBody UserPasswordCheckRequestDto requestDto) throws Exception {
        boolean checkPw =  authService.checkPassword(userId, requestDto);
        return ResponseEntity.ok().body(checkPw);
    }
}
