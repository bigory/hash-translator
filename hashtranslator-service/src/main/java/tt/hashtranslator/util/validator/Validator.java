package tt.hashtranslator.util.validator;

import tt.hashtranslator.entity.HashRequest;

public interface Validator {

    HashRequest validateApplication(String applicationFormatJson);

}
