package manito.springmanito.global.exception;


import manito.springmanito.global.dto.RestApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<RestApiException> processValidationError(JwtException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


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
}
