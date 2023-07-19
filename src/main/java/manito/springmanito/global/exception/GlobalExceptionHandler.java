package manito.springmanito.global.exception;


import io.jsonwebtoken.JwtException;
import manito.springmanito.global.dto.RestApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler({JwtException.class})
    public ResponseEntity<RestApiException> handleException(JwtException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<RestApiException> handleException(RuntimeException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiException> handleException(NullPointerException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}