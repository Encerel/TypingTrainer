package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.ExcerptDto;
import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ExcerptService {

    List<Excerpt> findAll();

    Optional<Excerpt> findById(long id);

    List<Excerpt> findAllByBookId(long id);

    List<Excerpt> findAllByBookName(String name);

    ResponseEntity<ServerResponse> saveAllExcerpts(List<Excerpt> excerpts);

    ResponseEntity<ServerResponse> createNewExcerpt(ExcerptDto excerptDTO);
}
