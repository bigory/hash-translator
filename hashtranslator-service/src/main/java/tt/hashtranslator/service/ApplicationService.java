package tt.hashtranslator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.entity.HashResponse;
import tt.hashtranslator.entity.Status;
import tt.hashtranslator.exception.ApplicationNotFoundException;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.util.converter.Converter;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final Converter converter;
    private final ApplicationRepository applicationRepository;

    public HashResponse getApplicationById(ObjectId id) {
        Map<String, String> hashes = applicationRepository.findApplicationById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("There are not application by id " + id))
                .getHashes();
        return converter.convertToHashResponse(hashes);
    }

    public Application createApplicationUser(Map<String, String> hashAndDecryptResult) {
        return applicationRepository.save(Application.builder()
                .id(ObjectId.get())
                .hashes(hashAndDecryptResult)
                .status(Status.IN_PROCESSING)
                .build());
    }

}
