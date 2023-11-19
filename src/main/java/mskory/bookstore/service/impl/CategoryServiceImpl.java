package mskory.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.CategoryDto;
import mskory.bookstore.mapper.CategoryMapper;
import mskory.bookstore.model.Category;
import mskory.bookstore.repository.CategoryRepository;
import mskory.bookstore.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(findCategoryById(id));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category mappedCategory = categoryMapper.toModel(categoryDto);
        Category savedCategory = categoryRepository.save(mappedCategory);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category updatedCategory = categoryMapper.update(findCategoryById(id), categoryDto);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category by id " + id));
    }
}
