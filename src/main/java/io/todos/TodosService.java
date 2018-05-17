package io.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TodosService {

    private RestTemplate restTemplate;

    private String serviceId = "localhost:8080";

    @Autowired
    public TodosService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Call client to get list, this query-api
     * @return
     */
    public List<Todo> listTodos() {

        URI uri = UriComponentsBuilder.fromUriString("//" + serviceId + "/")
            .build().toUri();

        Todo[] result = restTemplate.getForObject(uri, Todo[].class);
        if(result == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(result);
    }

    /**
     * Make a remote call to get a specific Todo
     * @param id
     * @return
     */
    public Todo getTodo(Integer id) {

        URI uri = UriComponentsBuilder.fromUriString("//" + serviceId + "/" + id)
            .build().toUri();

        return restTemplate.getForObject(uri, Todo.class);
    }
}

