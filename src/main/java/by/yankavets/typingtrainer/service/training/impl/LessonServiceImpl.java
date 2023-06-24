package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.mapper.CourseMapper;
import by.yankavets.typingtrainer.mapper.LessonMapper;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.service.training.CourseService;
import by.yankavets.typingtrainer.service.training.ExerciseService;
import by.yankavets.typingtrainer.service.training.LessonService;
import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.model.dto.LessonDto;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    private final ExerciseService exerciseService;
    private final LessonMapper lessonMapper;
    private final CourseMapper courseMapper;
    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository,
                             CourseService courseService,
                             @Lazy ExerciseService exerciseService,
                             LessonMapper lessonMapper, CourseMapper courseMapper) {
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
        this.exerciseService = exerciseService;
        this.lessonMapper = lessonMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findById(long id) {
        Lesson foundLesson = lessonRepository
                .findById(id)
                .orElseThrow(() -> new TrainingDataException(ExceptionMessage.WRONG_LESSON_ID));

        return foundLesson;
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> deleteById(long lessonId) {
        lessonRepository.deleteById(lessonId);
        ServerResponse response = MessageServerResponse.builder()
                .message(Message.LESSON_HAS_DELETED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> createLesson(LessonDto lessonDto) {
        Course foundCourse = courseService.findById(lessonDto.getCourseId());
        Lesson lesson = findById(lessonDto.getLessonId());
        lesson.setCourse(foundCourse);
        lessonRepository.save(lesson);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.LESSON_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    private List<Exercise> setUpLessonId(List<Exercise> exercises, Lesson lesson) {
        exercises.forEach(exercise -> exercise.setLesson(lesson));
        return exercises;
    }
}
