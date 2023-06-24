package by.yankavets.typingtrainer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResetPasswordDto {

    private String token;

    private String password;

    private String confirmedPassword;
}
