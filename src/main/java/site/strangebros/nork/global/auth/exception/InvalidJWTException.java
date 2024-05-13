package site.strangebros.nork.global.auth.exception;

public class InvalidJWTException extends RuntimeException {

    public InvalidJWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJWTException(String message) {
        super(message);
    }
}
