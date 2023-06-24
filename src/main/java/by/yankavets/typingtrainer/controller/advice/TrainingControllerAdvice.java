package by.yankavets.typingtrainer.controller.advice;

import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.exception.auth.IncorrectCredentialsException;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrainingControllerAdvice {

    @ExceptionHandler(TrainingDataException.class)
    public ResponseEntity<ServerResponse> handleTrainingDataException(
            TrainingDataException exception
    ) {
        ServerResponse serverResponse = AdviceErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(serverResponse, HttpStatus.BAD_REQUEST);
    }
}
