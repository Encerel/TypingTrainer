package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.CourseDto;
import by.yankavets.typingtrainer.model.dto.ExerciseDto;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {LessonMapper.class, ExerciseMapper.class}
)
public interface CourseMapper {
    
    Course toEntity(CourseDto courseDto);

    CourseDto toDto(Course course);

    List<CourseDto> toDtoList(List<Course> courses);
}
