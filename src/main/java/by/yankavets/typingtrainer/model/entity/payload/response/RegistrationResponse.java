package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationResponse implements ServerResponse {
    private String message;
    private int status;

}
