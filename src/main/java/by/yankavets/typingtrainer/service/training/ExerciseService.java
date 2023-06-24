package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExerciseService {

    List<Exercise> findAll();

    ResponseEntity<ServerResponse> deleteById(long exerciseId);

    ResponseEntity<ServerResponse> createExercise(ExerciseDto exerciseDTO);

    ExerciseDto findById(long exerciseId);

    ResponseEntity<ServerResponse> saveAll(List<Exercise> exercises);

    List<Exercise> findExercisesByUserIdAndCourseId(long userId, long courseId);

    void enableFirstExercises(long userId);

    List<Exercise> findByCourseAndUserWithStatistic(long userId, long courseId);

}
