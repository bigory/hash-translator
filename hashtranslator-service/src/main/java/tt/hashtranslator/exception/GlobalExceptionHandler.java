package tt.hashtranslator.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleHttpException(HttpClientErrorException exception) {
        log.error("{}, {}", exception.getStatusCode(), exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleRuntimeException(ApplicationNotFoundException exception) {
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

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("{}", exception.getMessage());
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        String messageException = String.format("%s %s %s", Objects.requireNonNull(fieldError).getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation exception format: " + messageException);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnrecognizedPropertyException(UnrecognizedPropertyException exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation exception structure: " + exception.getOriginalMessage());
    }

}
