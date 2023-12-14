package tt.authorization.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;
import tt.authorization.exception.UserNotFoundException;
import tt.authorization.exception.UsernameDuplicateException;
import tt.authorization.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameDuplicateException("User " + userDto.getUsername() + " already exists");
        }
        User user = User.builder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .role(userDto.getRole())
                .build();
        User savedUser = userRepository.save(user);
        log.info("Save user {} ", savedUser);
        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }

    public void deleteUser(Long userId) {
        try {
            log.info("Delete user where id {}", userId);
            userRepository.deleteById(userId);
        } catch (RuntimeException ex) {
            throw new UserNotFoundException("Invalid user id: " + userId);
        }
    }

}
