package mskory.bookstore.repository;

import java.util.List;
import mskory.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
