package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.dto.LessonDto;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {CourseMapper.class, ExerciseMapper.class}
)
public interface LessonMapper {

    @Mapping(source = "lessonId", target = "id")
    Lesson toEntity(LessonDto lessonDto);
    @Mapping(source = "id", target = "lessonId")
    LessonDto toDto(Lesson lesson);

    List<LessonDto> toDtoList(List<Lesson> lessons);
}
