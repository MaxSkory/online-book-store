package mskory.bookstore.mapper;

import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.book.BookDto;
import mskory.bookstore.dto.book.CreateBookRequestDto;
import mskory.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(CreateBookRequestDto bookDto);

    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toExistModel(@MappingTarget Book bookToUpdate, CreateBookRequestDto requestDto);
}
