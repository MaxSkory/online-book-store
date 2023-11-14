package mskory.bookstore.repository.book.specs;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(Record searchParameters);
}
