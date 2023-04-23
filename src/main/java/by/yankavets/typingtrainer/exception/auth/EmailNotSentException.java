package by.yankavets.typingtrainer.exception.auth;

public class EmailNotSentException extends RuntimeException {

    public EmailNotSentException() {
        super();
    }

    public EmailNotSentException(String message) {
        super(message);
    }

    public EmailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotSentException(Throwable cause) {
        super(cause);
    }

    protected EmailNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
