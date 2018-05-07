package io.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@SpringBootApplication
@EnableCircuitBreaker
public class TodosApp {

    /**
     * Top level application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(TodosApp.class);

    /**
     * Spring Boot Environment - having this is handy
     */
    private Environment environment;

    /**
     * Constructor that we inject with a Spring Boot Environment
     * @param environment
     */
    @Autowired
    public TodosApp(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public String getEndpoint() {
        return this.environment.getProperty("server.address")
            + ":"
            + this.environment.getProperty("server.port");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(TodosApp.class);
    }
}
