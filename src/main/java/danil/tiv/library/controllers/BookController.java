package danil.tiv.library.controllers;

import danil.tiv.library.store.entities.BookEntity;
import danil.tiv.library.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    //Вывод всех книг
    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    //Поиск по ID
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Добавление книги
    @PostMapping
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book);
    }

    //Обновление книги
    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable Long id, @RequestBody BookEntity bookDetails) {
        try {
            BookEntity updateBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updateBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Удаление книги
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
