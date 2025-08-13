package com.example.demo;

import org.junit.jupiter.api.Test; // JUnit 5 annotation for test methods
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Annotation for testing Spring MVC controllers
import org.springframework.test.web.servlet.MockMvc; // Used to simulate HTTP requests

import static org.hamcrest.Matchers.containsString; // For asserting string content
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // For building GET requests
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // For printing response details
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // For asserting response content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // For asserting HTTP status codes

@WebMvcTest(HelloController.class) // Focuses Spring Boot test on the HelloController only
public class HelloControllerTests {

    @Autowired // Injects MockMvc, which is used to test controllers without a full HTTP server
    private MockMvc mockMvc;

    @Test // Marks this method as a test case
    public void shouldReturnDefaultMessage() throws Exception {
        // Performs a GET request to /hello
        this.mockMvc.perform(get("/hello"))
            .andDo(print()) // Prints the request and response details (useful for debugging)
            .andExpect(status().isOk()) // Asserts that the HTTP status is 200 OK
            .andExpect(content().string(containsString("Hello, World!"))); // Asserts that the response body contains "Hello, World!"
    }

    @Test
    public void shouldReturnCustomMessage() throws Exception {
        // Performs a GET request to /hello with a 'name' parameter
        this.mockMvc.perform(get("/hello").param("name", "Azure"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello, Azure!")));
    }
}
