package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.dto.*;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDataResponse implements ServerResponse {
    private ExcerptDto excerptDTO;
    private BookDto bookDTO;

    private ExerciseDto exerciseDTO;

    private LessonDto lessonDTO;

    private CourseDto courseDTO;
}
