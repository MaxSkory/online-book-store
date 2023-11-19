package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.book.BookDto;
import mskory.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mskory.bookstore.dto.book.BookSearchParameters;
import mskory.bookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto create(CreateBookRequestDto bookDto);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> search(BookSearchParameters searchParameters);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId);
}
