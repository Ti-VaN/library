package danil.tiv.library.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String BOOK_QUEUE = "bookQueue"; //Очередь
    public static final String BOOK_EXCHANGE = "bookExchange"; //Обменник
    public static final String BOOK_ROUTING_KEY = "bookRoutingKey"; //Ключ маршрутизации

    // Создание очереди.
    @Bean
    public Queue bookQueue() {
        return new Queue(BOOK_QUEUE, true);
    }

    // Создание обменника сообщений
    @Bean
    public DirectExchange bookExchange() {
        return new DirectExchange(BOOK_EXCHANGE);
    }

    // Привязываем очередь к обменнику с использованием ключа маршрутизации
    @Bean
    public Binding bookBinding(Queue bookQueue, DirectExchange bookExchange) {
        return BindingBuilder.bind(bookQueue).to(bookExchange).with(BOOK_ROUTING_KEY);
    }
}