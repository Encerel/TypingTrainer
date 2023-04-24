package by.yankavets.typingtrainer.service.email.token;

import by.yankavets.typingtrainer.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiresAt;

    @Column(name = "reset_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime resetAt;
    
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
}
