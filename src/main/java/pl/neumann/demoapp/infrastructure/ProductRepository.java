package pl.neumann.demoapp.infrastructure;

import pl.neumann.demoapp.domain.Product;

import java.util.Collection;

public interface ProductRepository {
    void save(Product product);

    Product findById(String id);

    Product update(String id, Product updatedProduct);

    Product remove(String id);

    Collection<Product> findAll();
}
