package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import lombok.*;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse implements ServerResponse {
    private String message;

}
