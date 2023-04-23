package by.yankavets.typingtrainer.exception.auth;

public class InvalidEmailTokenException extends RuntimeException {

    public InvalidEmailTokenException() {
    }

    public InvalidEmailTokenException(String message) {
        super(message);
    }

    public InvalidEmailTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidEmailTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
