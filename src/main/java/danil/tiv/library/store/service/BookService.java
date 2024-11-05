package danil.tiv.library.store.service;

import danil.tiv.library.store.entities.BookEntity;
import danil.tiv.library.store.repository.BookRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static danil.tiv.library.config.RabbitMQConfig.BOOK_EXCHANGE;
import static danil.tiv.library.config.RabbitMQConfig.BOOK_ROUTING_KEY;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, RabbitTemplate rabbitTemplate) {
        this.bookRepository = bookRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    //Метод получения всех книг
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    //Метод получения книги по ID
    public Optional<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    //Метод добавления книги
    public BookEntity addBook(BookEntity book) {
        BookEntity savedBook = bookRepository.save(book);
        rabbitTemplate.convertAndSend(BOOK_EXCHANGE, BOOK_ROUTING_KEY, "Book Created: " + savedBook);
        return savedBook;
    }

    //Метод обновления книги
    public BookEntity updateBook(Long id, BookEntity bookDetail) {
        BookEntity updatedBook = bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetail.getTitle());
            book.setAuthor(bookDetail.getAuthor());
            book.setPublished(bookDetail.getPublished());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));

        rabbitTemplate.convertAndSend(BOOK_EXCHANGE, BOOK_ROUTING_KEY, "Book Updated: " + updatedBook);
        return updatedBook;
    }

    //Метод удаления книги
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        rabbitTemplate.convertAndSend(BOOK_EXCHANGE, BOOK_ROUTING_KEY, "Book Deleted with ID: " + id);
    }
}
