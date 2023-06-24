package by.yankavets.typingtrainer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserExerciseFoundDto {
    private long userId;
    private long courseId;
    private List<ExerciseDto> exercises;
}
