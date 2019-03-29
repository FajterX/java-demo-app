package pl.neumann.demoapp.infrastructure;

import pl.neumann.demoapp.domain.Product;

public interface ProductRepository {
    void save(Product product);

    Product findById(String id);
}
