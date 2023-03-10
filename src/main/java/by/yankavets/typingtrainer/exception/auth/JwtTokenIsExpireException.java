package by.yankavets.typingtrainer.exception.auth;

import io.jsonwebtoken.JwtException;


public class JwtTokenIsExpireException extends JwtException {

    public JwtTokenIsExpireException(String message) {
        super(message);
    }

    public JwtTokenIsExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
