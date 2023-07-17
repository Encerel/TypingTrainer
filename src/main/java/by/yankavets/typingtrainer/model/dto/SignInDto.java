package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignInDto {

    @Email(message = ExceptionMessage.WRONG_EMAIL, regexp =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotEmpty(message = ExceptionMessage.EMAIL_SHOULD_NOT_BE_EMPTY)
    private String email;


    @NotEmpty(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_NULL)
    @Size(min = 8, message = ExceptionMessage.PASSWORD_SHOULD_CONTAIN_AT_LEAST_EIGHT_CHARACTER)
    private String password;
}
