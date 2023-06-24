package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.LessonDto;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    Lesson findById(long id);

    ResponseEntity<ServerResponse> deleteById(long lessonId);

    ResponseEntity<ServerResponse> createLesson(LessonDto lessonDTO);
}
