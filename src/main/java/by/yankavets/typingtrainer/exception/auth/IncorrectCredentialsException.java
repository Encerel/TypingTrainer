package by.yankavets.typingtrainer.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class IncorrectCredentialsException extends AuthenticationException {

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
