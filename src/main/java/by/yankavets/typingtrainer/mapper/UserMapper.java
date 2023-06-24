package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.UserDto;
import by.yankavets.typingtrainer.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {LessonMapper.class, CourseMapper.class, ExerciseMapper.class}
)
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
}
