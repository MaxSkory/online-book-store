package mskory.bookstore.repository.book.specs.impl;

import jakarta.persistence.criteria.Predicate;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.book.specs.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "categories";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> {
            Predicate combinedPredicate = criteriaBuilder.conjunction();
            for (String param : params) {
                combinedPredicate = criteriaBuilder.and(combinedPredicate,
                        criteriaBuilder.isMember(param, root.get(getKey())));
            }
            return combinedPredicate;
        });
    }
}
