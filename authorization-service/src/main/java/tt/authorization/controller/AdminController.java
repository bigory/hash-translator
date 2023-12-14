package tt.authorization.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.authorization.dto.UserDto;
import tt.authorization.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/registry")
    public ResponseEntity<UserDto> registryUser(@Valid @RequestBody UserDto userDto) {
        log.info("Registry user for {}", userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.saveUser(userDto));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        log.info("Delete for user id {}", userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Delete for user id: " + userId);
    }
}
