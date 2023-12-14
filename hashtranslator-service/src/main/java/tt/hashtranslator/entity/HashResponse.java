package tt.hashtranslator.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class HashResponse {

    private List<Map<String, String>> hashes;

}
