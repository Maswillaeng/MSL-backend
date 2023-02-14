package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.ImportResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.ExternalHttpService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ExternalHttpService externalHttpService;

    @PostMapping("/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody String email) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody String nickname) {
        if (userRepository.existsByNickName(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toEntity();
        if (authService.joinDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(user);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) throws Exception {
        LoginResponseDto dto = authService.login(request);

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                                    dto.getTokenResponseDto().getACCESS_TOKEN());

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                                    dto.getTokenResponseDto().getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(new UserLoginResponseDto(dto.getNickName(), dto.getUserImage()));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        Long userId = UserContext.userData.get().getUserId();
        authService.removeRefreshToken(userId);
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=")
                .header("Set-Cookie", "REFRESH_TOKEN=")
                .body(ResponseDto.of(
                        HttpStatus.OK,
                        "로그아웃 성공")
                );
    }

    @AuthCheck
    @GetMapping("/updateToken")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN")
                                                            String refreshToken) throws Exception {


        System.out.println("accessToken = " + refreshToken);
        TokenResponseDto tokenDto = authService.updateAccessToken(refreshToken);
        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                                                    tokenDto.getACCESS_TOKEN());

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                                                    tokenDto.getREFRESH_TOKEN());
        System.out.println("토큰 다시 발급 후");
        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(ResponseDto.of("토큰이 재발급 되었습니다", null));
    }

    @PostMapping("/certifications") // 쓸일 없음
    public ResponseEntity<Objects> impUid(@RequestBody String imp_uid) throws Exception {

        System.out.println("imp_uid = " + imp_uid);
        String access_token = String.valueOf(externalHttpService.importGetToken());
        ResponseEntity<ImportResponseDto> dto = externalHttpService.importGetCertifications(imp_uid, access_token);
        authService.adultIdentify(dto.getBody().getBirth());

        return ResponseEntity.ok().build();
    }
}
