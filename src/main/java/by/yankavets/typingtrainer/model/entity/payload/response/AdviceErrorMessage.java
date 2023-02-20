package by.yankavets.typingtrainer.model.entity.payload.response;


import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;

import java.time.ZonedDateTime;

public class AdviceErrorMessage implements ServerResponse {


    private String message;
    private ZonedDateTime timestamp;

    public AdviceErrorMessage(String message) {
        this.message = message;
        this.timestamp = ZonedDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
