package tt.hashtranslator.util.converter;


import org.springframework.stereotype.Component;
import tt.hashtranslator.entity.HashResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ConverterImpl implements Converter {

    @Override
    public HashResponse convertToHashResponse(Map<String, String> hashesMap) {
        return HashResponse.builder()
                .hashes(Collections.singletonList(hashesMap))
                .build();
    }

    @Override
    public Map<String, String> converterToMap(List<String> hashesList) {
        Map<String, String> hashesMap = new HashMap<>();
        hashesList.forEach(key -> hashesMap.put(key, ""));
        return hashesMap;
    }

}
