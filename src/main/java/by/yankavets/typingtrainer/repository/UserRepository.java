package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUsersByEmail(String email);
}
