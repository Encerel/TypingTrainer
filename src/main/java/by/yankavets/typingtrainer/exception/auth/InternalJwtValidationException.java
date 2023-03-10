package by.yankavets.typingtrainer.exception.auth;

public class InternalJwtValidationException extends RuntimeException{

    public static final String ERROR_MESSAGE = "Exception in JWT Token validation filter!";

    public InternalJwtValidationException() {
        super(ERROR_MESSAGE);
    }
}
