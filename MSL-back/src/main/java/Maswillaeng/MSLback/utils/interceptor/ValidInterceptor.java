package Maswillaeng.MSLback.utils.interceptor;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.TokenUserData;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@ComponentScan
public class ValidInterceptor implements Ordered, HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod;

        if (!(handler instanceof HandlerMethod))
            return true;
        handlerMethod = (HandlerMethod) handler;

        ValidToken token = handlerMethod.getMethodAnnotation(ValidToken.class);
        if (token == null) {
            return true;
        }


        String accessToken = new String();
        String refreshToken = new String();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        System.out.println(uri);

        Cookie[] cookies = req.getCookies();
        if (cookies != null) { // 쿠키가 존재한다면
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "ACCESS_TOKEN":
                        accessToken = cookie.getValue();
                        break;
                    case "REFRESH_TOKEN":
                        refreshToken = cookie.getValue();
                }
            }
        }

        if (!refreshToken.equals("")) { // 리프레시 토큰이 있다면
            try {
                System.out.println("refresh Token");
                Claims claims = jwtTokenProvider.getClaims(refreshToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/api/login");
                return false;
            }
            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리 (401)

        } else if (!accessToken.equals("")) {
            try {
                Claims claims = jwtTokenProvider.getClaims(accessToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.setStatus(401);
                return false;
            }
            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
