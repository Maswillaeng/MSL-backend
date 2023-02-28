package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.dto.user.reponse.ImportResponseDto;
import Maswillaeng.MSLback.dto.user.request.ImportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalHttpService {

    private final RestTemplate restTemplate;
    private final String IMP_GET_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    private final String IMP_GET_CERTIFICATE_URL = "https://api.iamport.kr/certifications/";
    private final String AUTHORIZATION = "Authorization";

    @Value("${import.key}")
    private String KEY;

    @Value("${import.secret")
    private String secret;

    public ResponseEntity<String> importGetToken() {
        ImportRequestDto requestDto = ImportRequestDto.builder()
                                                        .key(KEY)
                                                        .secret(secret)
                                                        .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<ImportRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.exchange(IMP_GET_TOKEN_URL, HttpMethod.POST, entity, String.class);
    }


    public ResponseEntity<ImportResponseDto> importGetCertifications(String uid, String access_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, access_token);
        String url= IMP_GET_CERTIFICATE_URL + uid;
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ImportResponseDto.class);
    }
}
