package tt.hashtranslator.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tt.hashtranslator.entity.HashResponse;
import tt.hashtranslator.service.ApplicationService;
import tt.hashtranslator.service.HashTranslatorService;

@Slf4j

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final HashTranslatorService hashTranslatorService;
    private final ApplicationService applicationService;

    @PostMapping("/applications")
    public ResponseEntity<?> getHashesUser(@RequestBody String hashesRequest) {
        log.info("Hashes request {}", hashesRequest);
        ObjectId applicationId = hashTranslatorService.getApplicationIdAndAsyncDecryptHash(hashesRequest);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(applicationId.toString());
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<?> getApplicationUser(@PathVariable ObjectId id) {
        log.info("Application  id {}", id);
        HashResponse hashResponse = applicationService.getApplicationById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hashResponse);
    }

}
