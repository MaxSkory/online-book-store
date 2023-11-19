package mskory.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.book.BookDto;
import mskory.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mskory.bookstore.dto.book.BookSearchParameters;
import mskory.bookstore.dto.book.CreateBookRequestDto;
import mskory.bookstore.mapper.BookMapper;
import mskory.bookstore.model.Book;
import mskory.bookstore.model.Category;
import mskory.bookstore.repository.CategoryRepository;
import mskory.bookstore.repository.book.BookRepository;
import mskory.bookstore.repository.book.specs.SpecificationBuilder;
import mskory.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final SpecificationBuilder<Book> specificationBuilder;

    @Override
    public BookDto create(CreateBookRequestDto requestBook) {
        Set<Category> categoriesFromDb =
                Set.copyOf(categoryRepository.findAllById(requestBook.categoryIds()));
        checkCategoriesMatch(requestBook.categoryIds(), categoriesFromDb);
        Book mappedBook = bookMapper.toModel(requestBook);
        mappedBook.setCategories(categoriesFromDb);
        Book savedBook = repository.save(mappedBook);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book bookFromDb = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no book by id " + id + " in the database"));
        return bookMapper.toDto(bookFromDb);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId) {
        return repository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book bookToUpdate = repository.findById(id)
                .map(b -> bookMapper.toExistModel(b, requestDto))
                .orElse(bookMapper.toModel(requestDto));
        Book savedBook = repository.save(bookToUpdate);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> search(BookSearchParameters searchParameters) {
        return repository.findAll(specificationBuilder.build(searchParameters))
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private void checkCategoriesMatch(Set<Long> ids, Set<Category> categoriesFromDb) {
        if (ids.size() != categoriesFromDb.size()) {
            Set<Long> categoryIdsFromDb = bookMapper.categoryIds(categoriesFromDb);
            List<Long> notExistCategoryIds = ids.stream()
                    .filter(i -> !categoryIdsFromDb.contains(i))
                    .toList();
            throw new EntityNotFoundException(
                    "There are no categories with ids " + notExistCategoryIds);
        }
    }
}
