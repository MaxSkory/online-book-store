package mskory.bookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import mskory.bookstore.validation.Isbn;

public record CreateBookRequestDto(
        @NotNull
        @Size(min = 1, max = 30)
        String title,
        @NotNull
        @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z]([a-z])+\\s?){0,4}")
        String author,
        @NotNull
        @Isbn
        String isbn,
        @NotNull
        @Min(value = 1)
        BigDecimal price,
        String description,
        String coverImage,
        @NotEmpty
        Set<Long> categoryIds
) {
}
