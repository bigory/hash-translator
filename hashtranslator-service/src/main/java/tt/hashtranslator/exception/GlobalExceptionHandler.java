package tt.hashtranslator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleHttpException(HttpClientErrorException exception) {
        log.error("{}, {}", exception.getStatusCode(), exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @ExceptionHandler({ApplicationNotFoundException.class, IncorrectStructureRequestException.class, IncorrectFormatHashException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message exception:" + exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleConnectionException(IOException exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Message exception:" + exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        log.error("Missing request header: {}, {}", exception.getMessage(), exception.getHeaderName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message exception:" + exception.getMessage());
    }

}
