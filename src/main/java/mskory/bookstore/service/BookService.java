package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto save(CreateBookRequestDto bookDto);
}
