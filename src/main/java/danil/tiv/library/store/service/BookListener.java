package danil.tiv.library.store.service;

import danil.tiv.library.store.repository.BookRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookListener {
    private final BookRepository bookRepository;

    @Autowired
    public BookListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RabbitListener(queues = "bookQueue")
    public void handleBookMessage(String message) {
        if (message.startsWith("Book Created: ")) {
            System.out.println("Received creation message: " + message); //Уведомление о создании книги


        } else if (message.startsWith("Book Updated: ")) {
            System.out.println("Received update message: " + message); //Уведомление об обнговлении книги

        } else if (message.startsWith("Book Deleted with ID: ")) {
            System.out.println("Received deletion message: " + message);
            Long id = Long.parseLong(message.replace("Book Deleted with ID: ", "")); //Уведомление о удалении книги
            bookRepository.deleteById(id);
        }
    }
}
