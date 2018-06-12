package io.corbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TodosAPI {

    private static final Logger LOG = LoggerFactory.getLogger(TodosAPI.class);

    final int maxSize = 50;
    final LinkedHashMap<String, Todo> todos = new LinkedHashMap<String, Todo>() {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest) {
            return size() > maxSize;
        }
    };

    @GetMapping("/")
    public List<Todo> listTodos() {
        return new ArrayList<>(todos.values());
    }

    @PostMapping("/")
    public Todo createTodo(@RequestBody Todo todo) {
        String id = UUID.randomUUID().toString();
        LOG.debug("creating Todo for: " + id);
        todo.setId(id);
        todos.put(todo.getId(), todo);
        return todos.get(todo.getId());
    }

    @DeleteMapping("/")
    public void clean() {
        todos.clear();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id) {
        return todos.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable String id) {
        todos.remove(id);
    }

    @PatchMapping("/{id}")
    public Todo update(@PathVariable String id, @RequestBody Todo todo) {
        if(todo == null) {
            throw new IllegalArgumentException("todo cannot be null yo");
        }
        if(!todos.containsKey(id)) {
            throw new RuntimeException("cannot update a todo with that id: " + id);
        }
        Todo old = todos.get(id);
        if(!ObjectUtils.isEmpty(todo.getCompleted())) {
            old.setCompleted(todo.getCompleted());
        }

        if(!StringUtils.isEmpty(todo.getTitle())){
            old.setTitle(todo.getTitle());
        }
        return old;
    }
}
