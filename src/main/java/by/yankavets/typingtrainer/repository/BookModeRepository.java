package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.training.BookMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookModeRepository extends JpaRepository<BookMode, Long> {
}
