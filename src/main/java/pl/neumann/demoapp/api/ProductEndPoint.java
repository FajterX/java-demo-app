package pl.neumann.demoapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") String id) {
        try {
            ProductResponseDto productResponseDto = productFacade.findById(id);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
