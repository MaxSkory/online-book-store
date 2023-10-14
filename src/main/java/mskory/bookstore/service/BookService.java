package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.BookSearchParameters;
import mskory.bookstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto save(CreateBookRequestDto bookDto);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
