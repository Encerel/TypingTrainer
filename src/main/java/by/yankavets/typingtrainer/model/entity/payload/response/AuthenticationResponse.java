package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import lombok.*;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements ServerResponse {

    private String JWTToken;

    private String message;

}
