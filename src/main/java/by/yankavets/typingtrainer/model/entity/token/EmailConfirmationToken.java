package by.yankavets.typingtrainer.model.entity.token;

import by.yankavets.typingtrainer.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_token")
public class EmailConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(
            nullable = false,
            name = "token"
    )
    private String token;

    @Column(
            nullable = false,
            name = "created_at"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(
            nullable = false,
            name = "expires_at"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime confirmedAt;

    @ManyToOne()
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
}
