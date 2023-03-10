package by.yankavets.typingtrainer.controller.advice;

import by.yankavets.typingtrainer.exception.auth.IncorrectCredentialsException;
import by.yankavets.typingtrainer.exception.auth.JwtTokenIsExpireException;
import by.yankavets.typingtrainer.exception.auth.UserIsAlreadyExistException;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ServerResponse> handleIncorrectCredentialsException(
            IncorrectCredentialsException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsAlreadyExistException.class)
    public ResponseEntity<ServerResponse> handleUserIsExistException(
            UserIsAlreadyExistException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> handleUsernameNotFoundException(
            UsernameNotFoundException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtTokenIsExpireException.class)
    public ResponseEntity<ServerResponse> handleJWTTokenIsExpired(
            JwtTokenIsExpireException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }
}
