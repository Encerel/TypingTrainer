package by.yankavets.typingtrainer.exception.auth;

public class InvalidJWTTokenException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Invalid JWT Token in Bearer Header";


    public InvalidJWTTokenException() {
        super(ERROR_MESSAGE);
    }
}
