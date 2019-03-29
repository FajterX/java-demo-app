package pl.neumann.demoapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.neumann.demoapp.DemoappApplicationTests;
import pl.neumann.demoapp.domain.ProductFacade;
import pl.neumann.demoapp.domain.ProductRequestDto;
import pl.neumann.demoapp.domain.ProductResponseDto;

import static org.assertj.core.api.Assertions.*;

public class ProductEndPointTest extends DemoappApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        final String url = "http://localhost:" + port + "/products";
        final ProductRequestDto product = new ProductRequestDto("product_name");
        String productJson  = mapToJson(product);

        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(url, getHttpRequest(productJson), ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getName()).isEqualTo("product_name");
    }

    @Test
    public void shouldGetExistingProduct() {
        ProductRequestDto dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(dto);
        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(existingProduct);
    }

    @Test
    public void shouldGetNotExistingProduct() {
        final String url = "http://localhost:" + port + "/products/-1";

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    String mapToJson (ProductRequestDto productRequestDto) {
        try {
            return objectMapper.writeValueAsString(productRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> getHttpRequest(String json) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        return new HttpEntity<>(json, httpHeaders);
    }

    //update delete 404 na get kiedy nie ma produktu
}
