package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.mapper.ExerciseMapper;
import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.training.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {


    private final ExerciseService exerciseService;

    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseController(
            ExerciseService exerciseService,
            ExerciseMapper exerciseMapper) {
        this.exerciseService = exerciseService;
        this.exerciseMapper = exerciseMapper;
    }


    @GetMapping
    public List<Exercise> findAll() {
        return exerciseService.findAll();
    }
    @GetMapping("/{id}")
    private ExerciseDto findById(@PathVariable("id") long exerciseId) {
        return exerciseService.findById(exerciseId);
    }

    @GetMapping("/training")
    public List<ExerciseDto> findUserExercises(
            @RequestParam long userId,
            @RequestParam long courseId) {
        return exerciseMapper.toDtoList(exerciseService.findByCourseAndUserWithStatistic(userId, courseId));
    }


    @DeleteMapping
    public ResponseEntity<ServerResponse> deleteExerciseById(
            @RequestParam("id") long exerciseId) {
        return exerciseService.deleteById(exerciseId);
    }

    @PostMapping
    public ResponseEntity<ServerResponse> createNewExercise(
            @RequestBody ExerciseDto exerciseDTO
    ) {
        return exerciseService.createExercise(exerciseDTO);
    }
}
