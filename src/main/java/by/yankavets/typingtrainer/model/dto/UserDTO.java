package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.model.entity.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private long id;

    private String name;

    private String email;

    private Set<Role> roles;
    @JsonIgnore
    private String password;

    private LocalDate createdAt;
}
