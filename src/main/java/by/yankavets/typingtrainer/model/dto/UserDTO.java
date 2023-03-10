package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private long id;

    private String name;

    private String email;

    private Set<Role> roles;
    @JsonIgnore
    private String password;

    private LocalDate createdAt;
}
