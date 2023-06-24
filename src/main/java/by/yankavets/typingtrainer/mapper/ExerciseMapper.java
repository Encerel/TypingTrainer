package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {LessonMapper.class, CourseMapper.class}
)
public interface ExerciseMapper {

    @Mapping(source = "exerciseId", target = "id")
    @Mapping(source = "typingSpeed", target = "typingSpeed")
    @Mapping(source = "mistakeCount", target = "mistakeCount")
    Exercise toEntity(ExerciseDto exerciseDto);
    @Mapping(source = "typingSpeed", target = "typingSpeed")
    @Mapping(source = "id", target = "exerciseId")
    ExerciseDto toDto(Exercise exercise);

    List<ExerciseDto> toDtoList(List<Exercise> exercises);
}
