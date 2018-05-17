package io.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TodosAPI {

    private TodosService service;

    @Autowired
    public TodosAPI(TodosService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Todo> listTodos() {
        return service.listTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Integer id) {
        return service.getTodo(id);
    }
}
