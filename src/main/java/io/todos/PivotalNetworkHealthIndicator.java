package io.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PivotalNetworkHealthIndicator implements HealthIndicator {

    private RestTemplate restTemplate;

    @Autowired
    public PivotalNetworkHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        //restTemplate.getForEntity("https://network.pivotal.io", String.class).getStatusCode();
        return Health.up().build();
    }
}
