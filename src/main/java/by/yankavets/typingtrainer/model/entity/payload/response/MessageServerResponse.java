package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageServerResponse implements ServerResponse {

    private String message;
    private int status;

}
