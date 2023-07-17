package by.yankavets.typingtrainer.controller.advice;

import by.yankavets.typingtrainer.exception.auth.*;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ServerResponse> handleInvalidEmailToken(
            InvalidTokenException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccountIsAlreadyActivatedException.class)
    public ResponseEntity<ServerResponse> handleAccountWasActivated(
            AccountIsAlreadyActivatedException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenIsExpiredException.class)
    public ResponseEntity<ServerResponse> handleAccountWasActivated(
            TokenIsExpiredException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ServerResponse> handleEmailNotSentException(
            EmailNotSentException exception
    ) {
        ServerResponse serverResponse = AuthenticationResponse.builder()
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(serverResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errorResponse = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errorResponse.put(fieldName, errorMessage);
                }
        );

        return errorResponse;
    }
}
