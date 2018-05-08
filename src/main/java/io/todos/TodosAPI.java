package io.todos;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TodosAPI {

    private Map<Integer, Todo> todos = new HashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(TodosApp.class);

    private static Integer seq = 0;

    @GetMapping("/")
    public List<Todo> listTodos() {
        return new ArrayList<>(todos.values());
    }

    @PostMapping("/")
    @HystrixCommand(fallbackMethod = "altCreateTodo")
    public Todo createTodo(@RequestBody Todo todo) {
        LOG.debug("creating Todo " + todo);
        LOG.info("creating Todo " + todo);
        LOG.warn("creating Todo " + todo);
        LOG.trace("creating Todo " + todo);
        LOG.error("creating Todo " + todo);

        todo.setId(seq++);
        todos.put(todo.getId(), todo);
        return todos.get(todo.getId());
    }

    public Todo altCreateTodo(Todo todo) {
        return Todo.builder().title("Fix your createTodo method").build();
    }

    @DeleteMapping("/")
    public void clean() {
        LOG.debug("removing " + todos.size() + " todos");
        todos.clear();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Integer id) {
        return todos.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Integer id) {
        LOG.debug("removing todo " + id);
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
