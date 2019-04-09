package pl.neumann.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductListResponseDto {
    private final List<ProductResponseDto> products;

    @JsonCreator
    public ProductListResponseDto(@JsonProperty("products") List<ProductResponseDto> products) {
        this.products = products;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }
}
