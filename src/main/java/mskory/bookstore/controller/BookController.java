package mskory.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.book.BookDto;
import mskory.bookstore.dto.book.BookSearchParameters;
import mskory.bookstore.dto.book.CreateBookRequestDto;
import mskory.bookstore.service.BookService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Allows to manage books table")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Create new book", description = "Create new book record in DB")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookDto create(@RequestBody @Valid CreateBookRequestDto requestBook) {
        return bookService.create(requestBook);
    }

    @Operation(summary = "Get multiple books",
            description = "Get multiple books, "
                    + "allow pagination and sorting, "
                    + "20 books starts from id 1 by default ")
    @GetMapping
    public List<BookDto> getAll(@ParameterObject Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @Operation(summary = "Get book by id",
            description = "Return book record from DB by provided id")
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @Operation(summary = "Search books", description = "Search books by author and price")
    @GetMapping("/search")
    public List<BookDto> search(@Valid BookSearchParameters params) {
        return bookService.search(params);
    }

    @Operation(summary = "Update book by id",
            description = "Update book with new provided fields by provided id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookDto updateById(
            @PathVariable Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }

    @Operation(summary = "Delete book by id",
            description = "Soft delete book record by provided id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
