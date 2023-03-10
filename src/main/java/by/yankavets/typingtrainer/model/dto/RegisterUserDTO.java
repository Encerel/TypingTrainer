package by.yankavets.typingtrainer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserDTO {

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, message = "Name length should be at least 2 character!")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be empty!")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password should not be empty")
    private String password;

}