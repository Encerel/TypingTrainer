package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.dto.UserDTO;
import by.yankavets.typingtrainer.model.entity.Role;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse implements ServerResponse {


    private UserDTO userDTO;
    private String message;
    private int status;



}
