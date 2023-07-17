package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.mapper.ExerciseMapper;
import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.repository.ExerciseRepository;
import by.yankavets.typingtrainer.service.training.ExerciseService;
import by.yankavets.typingtrainer.service.training.LessonService;
import by.yankavets.typingtrainer.service.user.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> deleteById(long exerciseId) {
        exerciseRepository.deleteById(exerciseId);
        ServerResponse response = MessageServerResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Message.EXERCISE_HAS_DELETED)
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> createExercise(ExerciseDto exerciseDTO) {
        Lesson mappedLesson = lessonService.findById(exerciseDTO.getLessonId());
        Exercise exercise = new Exercise();
        exercise.setExercise(exerciseDTO.getExercise());
        exercise.setLesson(mappedLesson);

        exerciseRepository.save(exercise);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.EXERCISE_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ExerciseDto findById(long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new TrainingDataException(ExceptionMessage.WRONG_EXERCISE_ID)
        );
        return exerciseMapper.toDto(exercise);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> saveAll(List<Exercise> exercises) {

        exerciseRepository.saveAll(exercises);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.EXERCISES_HAVE_CREATED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Exercise> findExercisesByUserIdAndCourseId(long userId, long courseId) {

        User foundUser = userService.findById(userId);

        List<Exercise> sortedExercises = foundUser.getExercises().stream()
                .filter(exercise -> exercise.getLesson().getCourse().getId() == courseId)
                .sorted((ex1, ex2) -> Math.toIntExact(ex1.getId() - ex2.getId()))
                .toList();

        return sortedExercises;

    }

    @Override
    @Transactional
    public void enableFirstExercises(long userId) {
        exerciseRepository.enableFirstExercise(userId);
    }

    @Override
    public List<Exercise> findByCourseAndUserWithStatistic(long userId, long courseId) {

        return exerciseRepository.findWithAccess(userId, courseId).stream()
                .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
                .toList();
    }


}
