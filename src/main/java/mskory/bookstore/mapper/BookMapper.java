package mskory.bookstore.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.CreateBookRequestDto;
import mskory.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @JsonIgnoreProperties(ignoreUnknown = true)
    Book toModel(CreateBookRequestDto bookDto);

    BookDto toDto(Book book);
}
