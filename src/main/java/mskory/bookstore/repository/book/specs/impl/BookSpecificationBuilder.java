package mskory.bookstore.repository.book.specs.impl;

import java.lang.reflect.Field;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.exception.DataProcessingException;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.book.specs.SpecificationBuilder;
import mskory.bookstore.repository.book.specs.SpecificationProvider;
import mskory.bookstore.repository.book.specs.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specProviderManager;

    @Override
    public Specification<Book> build(Record searchParameters) {
        Specification<Book> spec = Specification.where(null);
        for (Field field : searchParameters.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(searchParameters) != null) {
                    SpecificationProvider<Book> provider =
                            specProviderManager.getProvider(field.getName());
                    String[] params = (String[]) field.get(searchParameters);
                    spec = spec.and(provider.getSpecification(params));
                }
            } catch (IllegalAccessException e) {
                throw new DataProcessingException("Can't build specification "
                        + "for search parameters " + searchParameters, e);
            }
        }
        return spec;
    }
}
