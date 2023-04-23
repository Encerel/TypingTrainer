package by.yankavets.typingtrainer.exception.auth;

public class AccountIsAlreadyActivatedException extends RuntimeException {

    public AccountIsAlreadyActivatedException() {
        super();
    }

    public AccountIsAlreadyActivatedException(String message) {
        super(message);
    }

    public AccountIsAlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountIsAlreadyActivatedException(Throwable cause) {
        super(cause);
    }

    protected AccountIsAlreadyActivatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
