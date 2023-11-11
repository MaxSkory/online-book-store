package mskory.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.BookSearchParameters;
import mskory.bookstore.dto.CreateBookRequestDto;
import mskory.bookstore.mapper.BookMapper;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.SpecificationBuilder;
import mskory.bookstore.repository.book.BookRepository;
import mskory.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final SpecificationBuilder<Book> specificationBuilder;
    private final BookRepository repository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestBook) {
        Book mappedBook = bookMapper.toModel(requestBook);
        Book savedBook = repository.save(mappedBook);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> findAll() {
        return repository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no book by id " + id + " in the database"));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book bookToUpdate = repository.findById(id)
                .map(b -> bookMapper.toExistModel(b, requestDto))
                .orElse(bookMapper.toModel(requestDto));
        Book savedBook = repository.save(bookToUpdate);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return repository.findAll(specificationBuilder.build(searchParameters))
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
