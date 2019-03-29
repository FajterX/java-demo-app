package pl.neumann.demoapp.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.neumann.demoapp.DemoappApplicationTests;

public class HelloWorldEndPointTest extends DemoappApplicationTests {

    @Test
    public void shouldReturnGreetings() {
        //given
        final String url = "http://localhost:" + port + "/hello";
        //when
        ResponseEntity<String> response = httpClient.getForEntity(url, String.class);
        //then
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isEqualTo("Hello Heroku World!");
    }
}
