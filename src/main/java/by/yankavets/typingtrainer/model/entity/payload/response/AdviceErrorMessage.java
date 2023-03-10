package by.yankavets.typingtrainer.model.entity.payload.response;


import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdviceErrorMessage implements ServerResponse {

    private String message;
    private String timestamp;

    private int status;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm:ss");


}
