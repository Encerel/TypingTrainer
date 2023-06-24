package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class SignUpDto {

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, message = "Name length should be at least 2 character!")
    private String name;


    @NotEmpty(message = "Email should not be empty!")
    @Email(message = ExceptionMessage.WRONG_EMAIL)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password should not be empty")
    private String password;

}