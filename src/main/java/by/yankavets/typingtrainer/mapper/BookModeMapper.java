package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.BookModeDto;
import by.yankavets.typingtrainer.model.entity.training.BookMode;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {ExcerptMapper.class}
)
public interface BookModeMapper {

    BookMode toEntity(BookModeDto bookModeDto);

    BookModeDto toDto(BookMode bookMode);

    List<BookModeDto> toDtoList(List<BookMode> bookModes);
}
