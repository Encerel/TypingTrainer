package by.yankavets.typingtrainer.exception.auth;

public class EmailTokenIsExpiredException extends RuntimeException{

    public EmailTokenIsExpiredException() {
    }

    public EmailTokenIsExpiredException(String message) {
        super(message);
    }

    public EmailTokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailTokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public EmailTokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
