package pl.neumann.demoapp.domain;

import org.springframework.stereotype.Component;
import pl.neumann.demoapp.infrastructure.ProductRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto findById(String id) {
        Product product = this.productRepository.findById(id);
        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        if (!productRequestDto.isValid()) {
            throw new RuntimeException("Product name cannot be empty!");
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product(id, productRequestDto.getName(), createdAt);

        productRepository.save(product);

        ProductResponseDto response = new ProductResponseDto(
                product.getId(),
                product.getName()
        );

        return response;
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto productRequestDto) {
        if (!productRequestDto.isValid()) {
            throw new RuntimeException("Product name cannot be empty!");
        }

        Product product = this.productRepository.findById(id);
        product.setName(productRequestDto.getName());
        product = this.productRepository.update(id, product);

        return new ProductResponseDto(product.getId(), product.getName());
    }
}
