package mskory.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.exception.DataProcessingException;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.BookRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory factory;

    @Override
    public Book save(Book book) {
        EntityManager manager = null;
        EntityTransaction transaction = null;
        try {
            manager = factory.createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save book " + book, e);
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        try (EntityManager manager = factory.createEntityManager()) {
            return manager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get books from the database", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager manager = factory.createEntityManager()) {
            return Optional.ofNullable(manager.find(Book.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
    }
}
