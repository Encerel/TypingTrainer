package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.model.entity.training.Exercise;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto {


    private long lessonId;
    private long courseId;
    private String name;
    private List<Exercise> exercises;

}
