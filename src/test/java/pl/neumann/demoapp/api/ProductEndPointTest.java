package pl.neumann.demoapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.neumann.demoapp.DemoappApplicationTests;
import pl.neumann.demoapp.domain.ProductFacade;
import pl.neumann.demoapp.domain.ProductListResponseDto;
import pl.neumann.demoapp.domain.ProductRequestDto;
import pl.neumann.demoapp.domain.ProductResponseDto;

import static org.assertj.core.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductEndPointTest extends DemoappApplicationTests {

    @Autowired
    private
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        //given
        final String url = "http://localhost:" + port + "/products";
        final ProductRequestDto product = new ProductRequestDto("product_name");
        String productJson  = mapToJson(product);

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(url, getHttpRequest(productJson), ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getName()).isEqualTo("product_name");
        assertThat(result.getBody().getId()).isNotEmpty();
    }

    @Test
    public void shouldGetExistingProduct() {
        //given
        ProductRequestDto dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(dto);
        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(existingProduct);
    }

    @Test
    public void shouldRemoveExistingProduct() {
        //given
        ProductRequestDto dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(dto);
        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        //when
        httpClient.delete(url);
        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldGetNotExistingProduct() {
        //given
        final String url = "http://localhost:" + port + "/products/-1";

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldUpdateProduct() {
        //given
        ProductRequestDto dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(dto);
        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();
        ProductRequestDto updatedProduct = new ProductRequestDto("newProdukt");
        String productJson  = mapToJson(updatedProduct);

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.exchange(url, HttpMethod.PUT, getHttpRequest(productJson), ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getName()).isEqualTo(updatedProduct.getName());
    }

    @Test
    public void shouldGetAllProducts() {
        //given
        ProductRequestDto dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct = productFacade.create(dto);
        dto = new ProductRequestDto("produkt");
        ProductResponseDto existingProduct2 = productFacade.create(dto);
        final String url = "http://localhost:" + port + "/products/";

        //when
        ResponseEntity<ProductListResponseDto> result = httpClient.getForEntity(url, ProductListResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getProducts().size()).isEqualTo(2);
        assertThat(result.getBody().getProducts().stream()
                .filter(productResponseDto -> productResponseDto.getId().equals(existingProduct.getId()))).isNotEmpty();
        assertThat(result.getBody().getProducts().stream()
                .filter(productResponseDto -> productResponseDto.getId().equals(existingProduct2.getId()))).isNotEmpty();
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
}
