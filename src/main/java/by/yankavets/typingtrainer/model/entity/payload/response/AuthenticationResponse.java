package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.dto.UserDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse implements ServerResponse {


    private UserDto userDTO;
    private String message;
    private int status;
    private String jwtToken;



}
