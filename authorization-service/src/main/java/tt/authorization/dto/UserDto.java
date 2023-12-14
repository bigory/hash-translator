package tt.authorization.dto;

import lombok.Builder;
import lombok.Data;
import tt.authorization.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Role role;
}