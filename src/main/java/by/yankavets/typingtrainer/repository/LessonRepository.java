package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.training.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
