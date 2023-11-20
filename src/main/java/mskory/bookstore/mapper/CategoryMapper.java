package mskory.bookstore.mapper;

import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.CategoryDto;
import mskory.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toModel(CategoryDto dto);

    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category update(@MappingTarget Category category, CategoryDto dto);
}
