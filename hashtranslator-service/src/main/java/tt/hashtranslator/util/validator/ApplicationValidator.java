package tt.hashtranslator.util.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tt.hashtranslator.exception.IncorrectFormatHashException;
import tt.hashtranslator.exception.IncorrectStructureRequestException;
import tt.hashtranslator.entity.HashRequest;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ApplicationValidator implements Validator {

    public HashRequest validateApplication(String applicationFormatJson) {
        HashRequest hashRequest = validateStructureApplication(applicationFormatJson);
        validateMD5Hash(hashRequest.getHashes());
        return hashRequest;
    }

    private void validateMD5Hash(List<String> hashes) {
        for (String hash : hashes) {
            boolean isValidHash = Pattern.matches("^[a-fA-F0-9]{32}$", hash);
            if (!isValidHash) {
                throw new IncorrectFormatHashException("Incorrect format hash: " + hash);
            }
        }
    }

    /**
     * Parse a JSON string representing a decryption request into a HashRequest object.
     *
     * @param applicationFormatJson JSON string representing a decryption request.
     * @return HashRequest object representing the request.
     * @throws IncorrectStructureRequestException if the request has an incorrect structure.
     */
    private HashRequest validateStructureApplication(String applicationFormatJson) throws IncorrectStructureRequestException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parsing JSON into JsonNode
            JsonNode jsonNode = mapper.readTree(applicationFormatJson);
            if (jsonNode.isEmpty() || jsonNode.size() > 1)
                throw new IncorrectStructureRequestException("The decryption request must have only one field \"hashes\"");
            String nameField = "hashes";
            if (!jsonNode.isObject() || !jsonNode.has(nameField) || !jsonNode.get(nameField).isArray())
                throw new IncorrectStructureRequestException("The decryption request must contain only one object with one field, the \"hashes\" array");
            if (jsonNode.get(nameField).isEmpty())
                throw new IncorrectStructureRequestException("In a decryption request, the \"hashes\" array cannot be empty");
            log.info("Structure request {}", applicationFormatJson);
            return mapper.readValue(applicationFormatJson, HashRequest.class);
        } catch (JsonProcessingException e) {
            throw new IncorrectStructureRequestException("Incorrect structure request");
        }
    }

}
