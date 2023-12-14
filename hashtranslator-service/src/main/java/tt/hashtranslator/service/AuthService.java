package tt.hashtranslator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tt.hashtranslator.config.HashTranslatorProperties;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final HashTranslatorProperties hashTranslatorProperties;

    public ResponseEntity<?> authUser(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationHeader);
        return restTemplate.exchange(hashTranslatorProperties.getUrlAuthService() + "/auth", HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

}