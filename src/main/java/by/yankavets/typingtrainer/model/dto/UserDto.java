package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.model.entity.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private long id;

    @NotEmpty(message = ExceptionMessage.NAME_SHOULD_NOT_BE_EMPTY)
    @Size(min = 2, message = ExceptionMessage.NAME_SHOULD_CONTAIN_AT_LEAST_TWO_CHARACTER)
    private String name;

    @Email(message = ExceptionMessage.WRONG_EMAIL, regexp =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotEmpty(message = ExceptionMessage.EMAIL_SHOULD_NOT_BE_EMPTY)
    private String email;

    private Set<Role> roles;

    @JsonIgnore
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private LocalDate createdAt;

    private boolean enabled;

    private boolean locked;
}
