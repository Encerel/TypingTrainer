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
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ServerResponse> handleUserNotCreatedException(
            UserNotCreatedException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.BAD_REQUEST);
    }




}
