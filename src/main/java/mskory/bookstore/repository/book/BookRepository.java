package mskory.bookstore.repository.book;

import java.util.List;
import lombok.NonNull;
import mskory.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("FROM Book b LEFT JOIN FETCH b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);

    @NonNull
    @Query("FROM Book b LEFT JOIN FETCH b.categories")
    Page<Book> findAll(@NonNull Pageable pageable);
}
