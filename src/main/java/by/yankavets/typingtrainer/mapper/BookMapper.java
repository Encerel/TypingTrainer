package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.BookDto;
import by.yankavets.typingtrainer.model.dto.BookModeDto;
import by.yankavets.typingtrainer.model.entity.training.Book;
import by.yankavets.typingtrainer.model.entity.training.BookMode;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {ExcerptMapper.class, BookModeMapper.class}
)
public interface BookMapper {

    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);

    List<BookDto> toDtoList(List<Book> books);
}
