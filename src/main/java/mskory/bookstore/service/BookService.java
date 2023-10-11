package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.BookSearchParameters;
import mskory.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto save(CreateBookRequestDto bookDto);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
