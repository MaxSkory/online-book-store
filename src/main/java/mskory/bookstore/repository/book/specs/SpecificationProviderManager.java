package mskory.bookstore.repository.book.specs;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getProvider(String key);
}
