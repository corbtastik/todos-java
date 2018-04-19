package io.corbs;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TodosAPI {

    private Map<Integer, Todo> todos = new HashMap<>();

    private static Integer seq = 0;

    @GetMapping("/")
    public List<Todo> listTodos() {
        return new ArrayList<>(todos.values());
    }

    @PostMapping("/")
    public Todo createTodo(@RequestBody Todo todo) {
        todo.setId(seq++);
        todos.put(todo.getId(), todo);
        return todos.get(todo.getId());
    }

    @DeleteMapping("/")
    public void clean() {
        todos.clear();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Integer id) {
        return todos.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Integer id) {
        todos.remove(id);
    }

    @PatchMapping("/{id}")
    public Todo update(@PathVariable Integer id, @RequestBody Todo todo) {
        if(!todos.containsKey(id)) {
            return Todo.builder().build();
        }
        Todo old = todos.get(id);
        old.setCompleted(todo.isCompleted());
        if(!StringUtils.isEmpty(todo.getTitle())){
            old.setTitle(todo.getTitle());
        }
        if(todo.getOrder() > -1) {
            old.setOrder(todo.getOrder());
        }
        return old;
    }
}
