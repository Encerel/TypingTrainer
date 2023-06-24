package by.yankavets.typingtrainer.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseResultDto {

    private long userId;

    private long exerciseId;

    private double mistakePercentage;

    private double typingSpeed;


}
