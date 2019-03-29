package pl.neumann.demoapp.domain;

public interface ProductFacade {
    // get

    ProductResponseDto findById(String id);
    // create

    ProductResponseDto create(ProductRequestDto productRequestDto);

    // update
    // delete
}
