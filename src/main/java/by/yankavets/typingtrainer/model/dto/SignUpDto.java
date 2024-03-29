package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.EmailConstant;
import by.yankavets.typingtrainer.constant.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class SignUpDto {

    @NotEmpty(message = ExceptionMessage.NAME_SHOULD_NOT_BE_EMPTY)
    @Size(min = 2, message = ExceptionMessage.NAME_SHOULD_CONTAIN_AT_LEAST_TWO_CHARACTER)
    private String name;

    @Valid
    @Email(message = ExceptionMessage.WRONG_EMAIL, regexp = EmailConstant.EMAIL_REGEX)
    @NotEmpty(message = ExceptionMessage.EMAIL_SHOULD_NOT_BE_EMPTY)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.PASSWORD_SHOULD_NOT_BE_NULL)
    @Size(min = 8, message = ExceptionMessage.PASSWORD_SHOULD_CONTAIN_AT_LEAST_EIGHT_CHARACTER)
    private String password;

}