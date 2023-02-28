package Maswillaeng.MSLback.configuration;

import Maswillaeng.MSLback.utils.interceptor.AuthInterceptor;
import Maswillaeng.MSLback.utils.interceptor.ValidInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    // private final TokenArgumentResolver tokenArgumentResolver;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidInterceptor validInterceptor() {
        return new ValidInterceptor();}
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOriginPatterns("*")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
                .allowCredentials(true)
                .maxAge(3000); // 이게 뭐죠?
   }

    public void addInterceptors(InterceptorRegistry registry) {
       // List<String> excludes = Arrays.asList("/favicon.ico");
        registry.addInterceptor(validInterceptor());
        registry.addInterceptor(authInterceptor());//.excludePathPatterns(excludes);
    }



}
