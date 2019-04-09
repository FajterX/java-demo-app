package pl.neumann.demoapp.domain;

import pl.neumann.demoapp.Exceptions.ProductNotFoundException;

public interface ProductFacade {
    ProductResponseDto findById(String id) throws ProductNotFoundException;

    ProductResponseDto create(ProductRequestDto productRequestDto);

    ProductResponseDto update(String id, ProductRequestDto productRequestDto) throws ProductNotFoundException;

    ProductResponseDto delete(String id) throws ProductNotFoundException;
}
