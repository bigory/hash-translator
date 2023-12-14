package tt.hashtranslator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import tt.hashtranslator.config.HashTranslatorProperties;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.entity.Status;
import tt.hashtranslator.repository.ApplicationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DecryptMD5HashService {

    private final String typeHash = "md5";
    private final WebClient webClient;
    private final ApplicationRepository applicationRepository;
    private final HashTranslatorProperties hashTranslatorProperties;

    public void decryptMD5Hash(Application application) {
        Flux.fromIterable(application.getHashes().keySet())
                .flatMap(param -> requestApiDecryptService(webClient, param))
                .subscribe(result -> {
                            application.getHashes().put(result.getT1(), result.getT2());
                            log.info("Decrypt result {}", result);
                        },
                        error -> log.error("Error in processing requests", error),
                        () -> {
                            application.setStatus(Status.PROCESSED);
                            applicationRepository.save(application);
                        });
    }

    private Mono<Tuple2<String, String>> requestApiDecryptService(WebClient webClient, String hash) {
        return webClient.get()
                .uri(hashTranslatorProperties.getUrlDecryptService(), uri ->
                        uri
                                .queryParam("type", typeHash)
                                .queryParam("hash", hash)
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(result -> Tuples.of(hash, result))
                .onErrorResume(throwable -> {
                    log.error("Decrypt service error {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

}
