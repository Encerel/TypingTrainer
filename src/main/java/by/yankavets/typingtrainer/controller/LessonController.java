package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.mapper.LessonMapper;
import by.yankavets.typingtrainer.model.dto.LessonDto;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.training.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {


    private final LessonService lessonService;

    private final LessonMapper lessonMapper;

    @Autowired
    public LessonController(LessonService lessonService, LessonMapper lessonMapper) {
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping
    public List<Lesson> findAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public LessonDto findById(@PathVariable("id") long id) {
        return lessonMapper.toDto(lessonService.findById(id));
    }

    @DeleteMapping
    public ResponseEntity<ServerResponse> deleteLessonById(
            @RequestParam("id") long lessonId) {
        return lessonService.deleteById(lessonId);
    }

    @PostMapping("/new")
    public ResponseEntity<ServerResponse> createNewLesson(
            @RequestBody LessonDto lessonDTO
    ) {
        return lessonService.createLesson(lessonDTO);
    }
}
