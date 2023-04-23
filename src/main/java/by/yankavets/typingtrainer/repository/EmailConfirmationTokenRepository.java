package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface EmailConfirmationTokenRepository
        extends JpaRepository<EmailConfirmationToken, Long> {

    Optional<EmailConfirmationToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE EmailConfirmationToken t SET t.confirmedAt = :confirmedAt WHERE t.token = :token")
    int updateConfirmedAt(@Param("token") String token,
                          @Param("confirmedAt") LocalDateTime confirmedAt);

}