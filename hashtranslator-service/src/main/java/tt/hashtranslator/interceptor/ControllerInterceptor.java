package tt.hashtranslator.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;
import tt.hashtranslator.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ControllerInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Authorization required");
        }
        ResponseEntity<?> responseEntity = authService.authUser(authHeader);
        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return true;
        response.sendError(responseEntity.getStatusCodeValue(), Objects.requireNonNull(responseEntity.getBody()).toString());
        return false;
    }

}

