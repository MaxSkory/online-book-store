package mskory.bookstore.mapper;

import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.BookDto;
import mskory.bookstore.dto.CreateBookRequestDto;
import mskory.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    Book toModel(CreateBookRequestDto bookDto);

    BookDto toDto(Book book);

    Book toExistModel(@MappingTarget Book bookToUpdate, CreateBookRequestDto requestDto);
}
