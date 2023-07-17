package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResetPasswordDto {

    @NotNull(message = ExceptionMessage.RESET_TOKEN_SHOULD_NOT_BE_NULL)
    @NotEmpty(message = ExceptionMessage.RESET_TOKEN_SHOULD_NOT_BE_EMPTY)
    private String token;

    @NotNull(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_NULL)
    @NotEmpty(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_EMPTY)
    private String password;

    @NotNull(message = ExceptionMessage.CONFIRMED_PASSWORD_SHOULD_NOT_BE_NULL)
    @NotEmpty(message = ExceptionMessage.CONFIRMED_PASSWORD_SHOULD_NOT_BE_EMPTY)
    private String confirmedPassword;
}
