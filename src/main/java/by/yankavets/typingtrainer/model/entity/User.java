package by.yankavets.typingtrainer.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@NoArgsConstructor
@ToString
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    @Email(message = "Incorrect email")
    @NotEmpty(message = "Email should not be empty")
    @NonNull
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    @NonNull
    private String password;


}
