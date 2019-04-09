package pl.neumann.demoapp.domain;

import org.springframework.stereotype.Component;
import pl.neumann.demoapp.Exceptions.ProductNotFoundException;
import pl.neumann.demoapp.infrastructure.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto findById(String id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id);
        if(product == null)
            throw new ProductNotFoundException();

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
    public ProductResponseDto update(String id, ProductRequestDto productRequestDto) throws ProductNotFoundException {
        if (!productRequestDto.isValid()) {
            throw new RuntimeException("Product name cannot be empty!");
        }
        Product product = this.productRepository.findById(id);
        if(product == null)
            throw new ProductNotFoundException();

        Product changedProduct = new Product(id, productRequestDto.getName(), product.getCreatedAt());
        this.productRepository.update(id, changedProduct);
        Product updatedProduct = this.productRepository.findById(id);

        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName());
    }

    @Override
    public ProductResponseDto delete(String id) throws ProductNotFoundException {
        Product product = this.productRepository.remove(id);
        if(product == null)
            throw new ProductNotFoundException();

        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductListResponseDto findAll() {
        Collection<Product> allProducts = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product: allProducts) 
            productResponseDtoList.add(new ProductResponseDto(product.getId(), product.getName()));

        return new ProductListResponseDto(productResponseDtoList);
    }
}
