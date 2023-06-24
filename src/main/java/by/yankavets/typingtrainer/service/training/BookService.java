package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.BookDto;
import by.yankavets.typingtrainer.model.entity.training.Book;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    Book findByName(String name);

    Book findById(long id);

    ResponseEntity<ServerResponse> deleteById(long bookId);

    List<Book> findAll();

    ResponseEntity<ServerResponse> save(BookDto bookDTO);

    ResponseEntity<ServerResponse> editBook(BookDto bookDTO);


}
