package mskory.bookstore.repository.book;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.model.Book;
import mskory.bookstore.repository.SpecificationProvider;
import mskory.bookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> providers;

    @Override
    public SpecificationProvider<Book> getProvider(String key) {
        return providers.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find "
                        + "book specification provider by key " + key));
    }
}
