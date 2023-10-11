package mskory.bookstore.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getProvider(String key);
}
