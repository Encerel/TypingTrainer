package by.yankavets.typingtrainer.model.entity.payload.response;


import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdviceErrorMessage implements ServerResponse {


    private String message;
//    private ZonedDateTime timestamp;

    private String timestamp;

    private int status;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm:ss");

    public AdviceErrorMessage(String message, int status) {
        this.message = message;
        this.timestamp = dateTimeFormatter.format(ZonedDateTime.now());
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
