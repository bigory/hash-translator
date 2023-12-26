package tt.hashtranslator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.hashtranslator.entity.HashRequest;
import tt.hashtranslator.entity.HashResponse;
import tt.hashtranslator.service.ApplicationService;
import tt.hashtranslator.service.HashTranslatorService;

import javax.validation.Valid;

@Slf4j

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final HashTranslatorService hashTranslatorService;
    private final ApplicationService applicationService;

    @PostMapping("/applications")
    public ResponseEntity<?> getHashesUser(@Valid @RequestBody HashRequest hashesRequest) {
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
