package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // This annotation marks the class as a REST controller, meaning it's ready for use by Spring MVC to handle web requests.
public class HelloController {

    // @GetMapping annotation maps HTTP GET requests to the /hello path.
    // It accepts an optional 'name' parameter. If not provided, it defaults to "World".
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        // This method returns a greeting string.
        return String.format("Hello, %s!", name);
    }
}
