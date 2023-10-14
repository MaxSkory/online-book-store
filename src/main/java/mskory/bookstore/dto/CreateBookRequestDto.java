package mskory.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NonNull;
import mskory.bookstore.annotation.Isbn;

@Data
public class CreateBookRequestDto {
    @NonNull
    @Size(min = 1, max = 30)
    private String title;

    @NonNull
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z]([a-z])+\\s?)*")
    private String author;

    @NonNull
    @Isbn
    @Pattern(regexp = "(ISBN\\s)((97)[89]-)?(\\d(-)?){10}")
    private String isbn;

    @NonNull
    @Min(value = 1)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
