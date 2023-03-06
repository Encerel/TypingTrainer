package by.yankavets.typingtrainer.controller.advice;

import by.yankavets.typingtrainer.exception.user.*;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ServerResponse> handleNotFoundUserException(UserNotFoundException exception) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ServerResponse> handleUserNotCreatedException(
            UserNotCreatedException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ServerResponse> handleIncorrectPasswordException(
            IncorrectPasswordException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsExistException.class)
    public ResponseEntity<ServerResponse> handleUserIsExistException(
            UserIsExistException exception
    ) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ServerResponse> handleIncorrectCredentialsException(IncorrectCredentialsException exception) {
        ServerResponse serverResponse = new AdviceErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(serverResponse, HttpStatus.UNAUTHORIZED);
    }
}
