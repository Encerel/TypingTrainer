package by.yankavets.typingtrainer.controller.advice;

import by.yankavets.typingtrainer.exception.auth.IncorrectCredentialsException;
import by.yankavets.typingtrainer.exception.auth.UserIsAlreadyExistException;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ServerResponse> handleIncorrectCredentialsException(
            IncorrectCredentialsException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ServerResponse> handleIncorrectCredentialsException(
            BadCredentialsException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ServerResponse> handleIncorrectCredentialsException(
            AuthenticationException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsAlreadyExistException.class)
    public ResponseEntity<ServerResponse> handleUserIsExistException(
            UserIsAlreadyExistException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> handleUsernameNotFoundException(
            UsernameNotFoundException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ServerResponse> handleJwtException(
            JwtException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }




}
