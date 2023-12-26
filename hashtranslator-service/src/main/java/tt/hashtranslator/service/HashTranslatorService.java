package tt.hashtranslator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.entity.HashRequest;
import tt.hashtranslator.util.converter.Converter;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashTranslatorService {

    private final Converter converter;
    private final ApplicationService applicationService;
    private final DecryptMD5HashService decryptMD5HashService;

    public ObjectId getApplicationIdAndAsyncDecryptHash(HashRequest hashRequest) {
        Map<String, String> hashesMap = converter.converterToMap(hashRequest.getHashes());
        Application application = applicationService.createApplicationUser(hashesMap);
        log.info("Application: {}", application);
        decryptMD5HashService.decryptMD5Hash(application);
        return application.getId();
    }

}