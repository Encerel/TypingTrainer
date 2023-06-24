package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.CourseDto;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findByName(String name);

    ResponseEntity<ServerResponse> deleteById(long id);

    ResponseEntity<ServerResponse> createCourse(CourseDto courseDTO);

    Course findById(long courseId);
}
