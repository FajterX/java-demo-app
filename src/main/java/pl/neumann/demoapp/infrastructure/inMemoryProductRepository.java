package pl.neumann.demoapp.infrastructure;

import org.springframework.stereotype.Repository;
import pl.neumann.demoapp.domain.Product;

import java.util.HashMap;
import java.util.Map;

@Repository
class inMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(String id) {
        return products.get(id);
    }

    @Override
    public Product update(String id, Product updatedProduct) {
        return products.replace(id, updatedProduct);
    }

    @Override
    public Product remove(String id) {
        return products.remove(id);
    }
}
