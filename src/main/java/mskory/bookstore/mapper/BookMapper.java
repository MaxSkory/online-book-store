package mskory.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.book.BookDto;
import mskory.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mskory.bookstore.dto.book.CreateBookRequestDto;
import mskory.bookstore.model.Book;
import mskory.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto bookDto);

    @Mapping(target = "categoryIds", source = "categories")
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toExistModel(@MappingTarget Book bookToUpdate, CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    default Set<Long> categoryIds(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }
}
