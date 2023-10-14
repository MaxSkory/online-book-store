package mskory.bookstore.repository.book.specs;

import java.math.BigDecimal;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "price";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        BigDecimal minPrice = new BigDecimal(params[0]);
        BigDecimal maxPrice = params.length > 1 ? new BigDecimal(params[1])
                : BigDecimal.valueOf(Long.MAX_VALUE);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }
}
