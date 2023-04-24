package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.service.email.token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE PasswordResetToken t SET t.resetAt = :resetAt WHERE t.token = :token")
    int updateResetAt(@Param("token") String token,
                          @Param("resetAt") LocalDateTime resetAt);

}
