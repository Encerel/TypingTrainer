package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.CourseDto;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.training.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }


    @GetMapping("/{id}")
    public Course findById(@PathVariable("id") long courseId) {
        return courseService.findById(courseId);
    }

    @GetMapping("/search")
    public Course findCourseByName(
            @RequestParam("name") String courseName) {
        return courseService.findByName(courseName);
    }

    @DeleteMapping
    public ResponseEntity<ServerResponse> deleteCourseById(
            @RequestParam("id") long courseId) {
        return courseService.deleteById(courseId);
    }

    @PostMapping("/new")
    public ResponseEntity<ServerResponse> createNewCourse(
            @RequestBody CourseDto courseDTO
    ) {
        return courseService.createCourse(courseDTO);
    }
}
