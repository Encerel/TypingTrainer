package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.mapper.BookMapper;
import by.yankavets.typingtrainer.model.dto.BookDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.model.entity.training.Book;
import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import by.yankavets.typingtrainer.repository.BookRepository;
import by.yankavets.typingtrainer.service.training.BookService;
import by.yankavets.typingtrainer.service.training.ExcerptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ExcerptService excerptService;
    @Autowired
    private BookMapper bookMapper;


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        Book foundBook = bookRepository.findById(id).orElseThrow(
                () -> new TrainingDataException(ExceptionMessage.WRONG_BOOK_ID)
        );
        return foundBook;
    }

    @Override
    public Book findByName(String name) {
        Book foundBook = bookRepository.findByName(name).orElseThrow(
                () -> new TrainingDataException(ExceptionMessage.WRONG_BOOK_NAME)
        );
        return foundBook;
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> deleteById(long bookId) {

        bookRepository.deleteById(bookId);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.EXCERPT_HAS_DELETED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> save(BookDto bookDTO) {

        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setExcerpts(bookDTO.getExcerpts());
        Book savedBook = bookRepository.save(book);

        if (bookDTO.getExcerpts() != null) {
            excerptService.saveAllExcerpts(setUpBookId(bookDTO.getExcerpts(), savedBook));
        }

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.BOOK_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> editBook(BookDto bookDTO) {
        Book book = new Book();
        book.setName(book.getName());
        ServerResponse response = MessageServerResponse.builder()
                .message(Message.BOOK_INFO_HAS_CHANGED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    private List<Excerpt> setUpBookId(List<Excerpt> excerpts, Book book) {
       excerpts.forEach(excerpt -> excerpt.setBook(book));
       return excerpts;
    }


}
