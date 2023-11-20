package mskory.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.CategoryDto;
import mskory.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mskory.bookstore.service.BookService;
import mskory.bookstore.service.CategoryService;
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

@Tag(name = "Categories management", description = "Allows to manage categories table")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Create new category", description = "Create new category record in DB")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryDto create(@RequestBody CategoryDto requestDto) {
        return categoryService.save(requestDto);
    }

    @Operation(summary = "Get multiple categories",
            description = "Get multiple categories, "
                    + "allow pagination and sorting, "
                    + "20 categories per page,"
                    + " starts from id 1 by default ")
    @GetMapping
    public List<CategoryDto> getAll(@ParameterObject Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @Operation(summary = "Get category by id",
            description = "Return category record from DB by provided id")
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Get all books by category id",
            description = "Return all book  records by category id, "
                    + "allow pagination and sorting, "
                    + "20 books per page by default ")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return bookService.getBooksByCategoryId(id);
    }

    @Operation(summary = "Update category by id",
            description = "Update category with new provided fields by provided id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryDto updateById(@PathVariable Long id, @RequestBody CategoryDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @Operation(summary = "Delete category by id",
            description = "Soft delete approach is in use.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
