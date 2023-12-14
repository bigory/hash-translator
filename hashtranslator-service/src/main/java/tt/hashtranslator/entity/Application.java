package tt.hashtranslator.entity;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Data
@Builder
@ToString
@Document("applications")
public class Application {

    private ObjectId id;
    private Map<String, String> hashes;
    private Status status;

}
