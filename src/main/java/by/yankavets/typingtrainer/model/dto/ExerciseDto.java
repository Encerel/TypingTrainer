package by.yankavets.typingtrainer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseDto {
    private long exerciseId;
    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long lessonId;

    private double typingSpeed;

    private double mistakeCount;
    @NotNull
    @NotEmpty
    private String exercise;
    @NotEmpty
    @NotNull
    private boolean access;
}
