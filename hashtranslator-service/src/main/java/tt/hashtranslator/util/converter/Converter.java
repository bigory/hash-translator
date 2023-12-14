package tt.hashtranslator.util.converter;

import tt.hashtranslator.entity.HashResponse;

import java.util.List;
import java.util.Map;

public interface Converter {

    Map<String, String> converterToMap(List<String> hashesList);

    HashResponse convertToHashResponse(Map<String, String> hashesMap);

}
