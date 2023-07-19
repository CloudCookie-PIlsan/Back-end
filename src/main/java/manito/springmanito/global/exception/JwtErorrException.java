package manito.springmanito.global.exception;

public class JwtErorrException extends RuntimeException{
    public JwtErorrException(String message) {
        super(message);
    }
}
