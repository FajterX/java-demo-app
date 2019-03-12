package pl.neumann.demoapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldEndPoint {

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    String hello() {
        return "Hello World!";
    }
}
