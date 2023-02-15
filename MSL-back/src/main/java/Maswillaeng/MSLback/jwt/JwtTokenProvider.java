package Maswillaeng.MSLback.jwt;

import Maswillaeng.MSLback.domain.entity.RoleType;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${secret.access}")
    private String SECRET_KEY;

    public static final long ACCESS_TOKEN_VALID_TIME = 1000 * 60 * 60; // 1시간
//    private final long ACCESS_TOKEN_VALID_TIME = 1; // 만료 테스트
    public static final long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 60 * 24; // 24시간

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }


    public String createAccessToken(Long userId, RoleType roleType) {

        Claims claims = Jwts.claims().setSubject("ATK");
        claims.put("userId", userId);
        claims.put("role", roleType);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }


    public String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims().setSubject("RTK");
        claims.put("userId", userId);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Claims getClaims(String token) throws JwtException {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (JwtException e) {
            throw new JwtException("잘못된 토큰입니다.", e);
        }
    }

    public Optional<Long> getUserIdWithoutException(String token) {
        try {
            Long userId = this.getClaims(token).get("userId", Long.class);
            return Optional.of(userId);
        } catch (ExpiredJwtException exception) {
            return Optional.empty();
        }
    }
}
