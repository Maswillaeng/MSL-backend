package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AESEncryption;
import Maswillaeng.MSLback.utils.auth.UserContext;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Maswillaeng.MSLback.jwt.JwtTokenProvider.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AESEncryption aesEncryption;

    public void join(User user) throws Exception {
        String encryptPwd = aesEncryption.encrypt(user.getPassword());
        user.resetPassword(encryptPwd);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        String encryptedPassword = aesEncryption.encrypt(requestDto.getPassword());

        if (!user.getPassword().equals(encryptedPassword)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        } else if (user.getWithdrawYn() == 1) {
            throw new EntityNotFoundException("탈퇴한 회원입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken);
        TokenResponseDto token = TokenResponseDto.builder().
                ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
                .build();
        return LoginResponseDto.builder()
                .tokenResponseDto(token)
                .nickName(user.getNickName())
                .userImage(user.getUserImage())
                .build();
    }

    public TokenResponseDto updateAccessToken(String refresh_token){
        String updateAccessToken;

           User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
           String OriginalRefreshToken = user.getRefreshToken();
           if (OriginalRefreshToken.equals(refresh_token)) {
               updateAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
           } else {
               user.destroyRefreshToken();
               userRepository.save(user);
               throw new JwtException("변조된 토큰");
           }

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .build();
    }

    public ResponseCookie getAccessTokenCookie(String accessToken){
        return ResponseCookie.from(
                        "ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_VALID_TIME)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie getRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "REFRESH_TOKEN", refreshToken)
                .path("/updateToken")
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_VALID_TIME)
                .sameSite("Lax")
                .build();
    }

    public boolean joinDuplicate(User user) {
        return userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail());
    }

    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }

    public void adultIdentify(String birth){
        LocalDate birthDate = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int userAge = LocalDate.now().getYear() - birthDate.getYear();
        if (birthDate.getDayOfYear() > LocalDate.now().getDayOfYear()) {
            userAge--;
        }
        if (userAge < 18) {
            throw new IllegalStateException("성인이 아닙니다");
        }
    }
}
