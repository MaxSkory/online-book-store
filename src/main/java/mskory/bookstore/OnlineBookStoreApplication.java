package mskory.bookstore;

import java.math.BigDecimal;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }
}
