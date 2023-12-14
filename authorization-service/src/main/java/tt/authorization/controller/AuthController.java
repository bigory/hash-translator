package tt.authorization.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {


    @GetMapping("/auth")
    public ResponseEntity<String> authUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
