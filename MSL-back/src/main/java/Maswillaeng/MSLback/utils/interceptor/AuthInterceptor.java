package Maswillaeng.MSLback.utils.interceptor;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.TokenUserData;
import Maswillaeng.MSLback.utils.auth.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ComponentScan
@NoArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod;

        // AuthCheck 어노테이션이 없는 경우 처리 x
        if (!(handler instanceof HandlerMethod))
            return true;
        handlerMethod = (HandlerMethod) handler;

        if (!handlerMethod.hasMethodAnnotation(AuthCheck.class)) {
            return true;
        }


        // 쿠키에서 토큰 가져오기
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;
        if(cookies != null) { // 쿠키가 있으면
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ACCESS_TOKEN")) {
                    accessToken = cookie.getValue();
                } else if (cookie.getName().equals("REFRESH_TOKEN")) {
                    refreshToken = cookie.getValue();
                }
            }
        } else { // 쿠키가 없으면 401
            response.sendError(HttpStatus.UNAUTHORIZED.value(),
                            "토큰을 담은 쿠키가 존재하지 않습니다.");
            return false;
        }

        if (refreshToken != null && request.getRequestURI().equals("/updateToken")) { // 리프레시 토큰 값이 있고, 요청 주소가 /updateToken이라면
            try {
                Claims claims = jwtTokenProvider.getClaims(refreshToken);
                UserContext.userData.set(new TokenUserData(
                        claims.get("userId", Long.class), null));
            } catch (ExpiredJwtException exception) { // 만료된 토큰이라면 로그인으로 리다이렉션
                response.sendRedirect("/login");
                return false;
            }catch (JwtException exception) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(),"잘못된 토큰입니다.");
                return false;
            }
        } else if(accessToken != null){ // 엑세스 토큰 값이 있다면
            try {
                Claims claims = jwtTokenProvider.getClaims(accessToken);
                UserContext.userData.set(new TokenUserData(
                        claims.get("userId", Long.class),
                        claims.get("role", String.class)));
            } catch (ExpiredJwtException exception) { // 만료된 토큰이라면 재발급
                log.info("만료된 엑세스 토큰입니다.");
                response.sendRedirect("/updateToken");
                return false;
            } catch (JwtException exception) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(),"잘못된 토큰입니다.");
                return false;
            }
        }



        // 권한 체크
        AuthCheck authCheck = handlerMethod.getMethodAnnotation(AuthCheck.class);
        String requiredRole = authCheck.role().toString();
        String userRole = UserContext.userData.get().getUserRole();

        if (!requiredRole.equals(userRole) && !requiredRole.equals("ALL")) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "권한이 없습니다.");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.remove(); // 쓰레드 로컬 지워주기.
    }

}
