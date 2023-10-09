package mskory.bookstore.repository;

import java.util.List;
import java.util.Optional;
import mskory.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> getAll();

    Optional<Book> findById(Long id);
}
