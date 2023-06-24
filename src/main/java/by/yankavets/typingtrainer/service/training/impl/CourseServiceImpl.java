package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.mapper.CourseMapper;
import by.yankavets.typingtrainer.model.dto.CourseDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.repository.CourseRepository;
import by.yankavets.typingtrainer.service.training.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(long courseId) {
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new TrainingDataException(ExceptionMessage.WRONG_COURSE_ID)
                );
        return foundCourse;
    }
    @Override
    public Course findByName(String name) {

        Optional<Course> courseFromDB = courseRepository.findByName(name);
        return courseFromDB.orElseThrow(
                () -> new TrainingDataException(ExceptionMessage.WRONG_COURSE_NAME)
        );
    }

    @Override
    public ResponseEntity<ServerResponse> deleteById(long id) {

        courseRepository.deleteById(id);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.COURSE_HAS_DELETED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServerResponse> createCourse(CourseDto courseDTO) {

        Course course = new Course();

        course.setDescription(courseDTO.getDescription());
        course.setName(courseDTO.getName());
        course.setLessons(Collections.emptyList());


        courseRepository.save(course);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.COURSE_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }


}
