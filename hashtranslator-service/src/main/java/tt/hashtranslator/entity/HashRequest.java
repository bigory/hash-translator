package tt.hashtranslator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashRequest {

    @NotEmpty
    private List<@NotBlank @Pattern(regexp = "^[a-fA-F0-9]{32}$") String> hashes;

}
