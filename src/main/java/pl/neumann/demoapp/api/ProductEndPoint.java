package pl.neumann.demoapp.api;

import org.springframework.web.bind.annotation.*;
import pl.neumann.demoapp.domain.ProductFacade;
import pl.neumann.demoapp.domain.ProductRequestDto;
import pl.neumann.demoapp.domain.ProductResponseDto;

@RestController
@RequestMapping(path = "/products")
class ProductEndPoint {

    private final ProductFacade productFacade;

    ProductEndPoint(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping()
    ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productFacade.create(productRequestDto);
    }

    @GetMapping("/{id}")
    ProductResponseDto getProduct(@PathVariable("id") String id) {
        return productFacade.findById(id);
    }
}
