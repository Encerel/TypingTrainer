package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.mapper.BookMapper;
import by.yankavets.typingtrainer.mapper.ExcerptMapper;
import by.yankavets.typingtrainer.model.dto.ExcerptDto;
import by.yankavets.typingtrainer.model.entity.training.Book;
import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.repository.ExcerptRepository;
import by.yankavets.typingtrainer.service.training.BookService;
import by.yankavets.typingtrainer.service.training.ExcerptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExcerptServiceImpl implements ExcerptService {

    @Autowired
    private ExcerptRepository excerptRepository;

    private final BookService bookService;

    @Autowired
    private ExcerptMapper excerptMapper;

    private BookMapper bookMapper;
    @Autowired
    public ExcerptServiceImpl(@Lazy BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public List<Excerpt> findAll() {
        return excerptRepository.findAll();
    }

    @Override
    public Optional<Excerpt> findById(long id) {
        return excerptRepository.findById(id);
    }
    @Override
    public List<Excerpt> findAllByBookId(long id) {
        return excerptRepository.findAllByBookId(id);
    }

    @Override
    public List<Excerpt> findAllByBookName(String name) {
        return excerptRepository.findAllByBookName(name);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> saveAllExcerpts(List<Excerpt> excerpts) {

        excerptRepository.saveAll(excerpts);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.ALL_EXCERPTS_HAVE_ADDED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> createNewExcerpt(ExcerptDto excerptDTO) {

        Book foundBook = bookService.findById(excerptDTO.getBookId());


        Excerpt excerpt = new Excerpt();

        excerpt.setExcerpt(excerptDTO.getExcerpt());
        excerpt.setBook(foundBook);

        excerptRepository.save(excerpt);
        ServerResponse response = MessageServerResponse.builder()
                .message(Message.EXCERPT_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }
}
