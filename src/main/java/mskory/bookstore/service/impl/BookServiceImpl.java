package mskory.bookstore.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.CreateBookRequestDto;
import mskory.bookstore.mapper.BookMapper;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.BookRepository;
import mskory.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAll() {
        return repository.getAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no book by id " + id + " in the database"));
        return bookMapper.toDto(book);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    public BookDto save(CreateBookRequestDto requestBook) {
        Book mappedBook = bookMapper.toModel(requestBook);
        Book savedBook = repository.save(mappedBook);
        return bookMapper.toDto(savedBook);
    }
}
