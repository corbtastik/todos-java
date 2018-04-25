package io.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableCircuitBreaker
public class TodosApp {

    private Environment environment;

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

	public static void main(String[] args) {
		SpringApplication.run(TodosApp.class, args);
	}
}
