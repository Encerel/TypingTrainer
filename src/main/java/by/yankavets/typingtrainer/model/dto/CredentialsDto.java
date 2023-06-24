package by.yankavets.typingtrainer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CredentialsDto {

    @Email
    @NotEmpty(message = "Email should not be empty!")
    private String email;


    @NotEmpty(message = "Password should not be empty!")
    @Size(min = 8, message = "Password should have 8 characters or more")
    private String password;
}
