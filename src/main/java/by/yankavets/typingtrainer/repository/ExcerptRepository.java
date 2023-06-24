package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcerptRepository extends JpaRepository<Excerpt, Long> {

    List<Excerpt> findAllByBookId(long id);

    List<Excerpt> findAllByBookName(String name);
}
