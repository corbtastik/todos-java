package io.todos;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Timed
public class TodosAPI {

    /**
     * In-memory map of Todos, confined by todos.limit property
     */
    private Map<Integer, Todo> todos = new HashMap<>();

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(TodosApp.class);

    /**
     * Internal id sequence - this isn't a global ID, local only
     */
    private static Integer seq = 0;

    /**
     * This counter metric will be available in JMX Console and Prometheus
     */
    private Counter hadIdCounter;

    /**
     * How many times the alternative flow is called
     */
    private Counter altCreateCounter;

    /**
     * To show how to populate audit repo with data: see auditevent actuator
     */
    private AuditEventRepository auditEventRepository;

    /**
     * General Todos application config instance
     */
    private TodosConfig config;

    /**
     * Constructor - inject MeterRegistry, AuditEventRepository, TodosConfig beans
     * @param registry
     * @param auditEventRepository
     * @param config
     */
    @Autowired
    public TodosAPI(MeterRegistry registry,
        AuditEventRepository auditEventRepository,
        TodosConfig config) {

        this.hadIdCounter = registry.counter("todo.hadId");
        this.altCreateCounter = registry.counter("todo.altCreate");
        this.auditEventRepository = auditEventRepository;
        this.config = config;
    }

    @GetMapping("/")
    public List<Todo> listTodos() {
        return new ArrayList<>(todos.values());
    }

    @PostMapping("/")
    @HystrixCommand(fallbackMethod = "altCreateTodo")
    public Todo createTodo(@RequestBody Todo todo) {
        if(todo.getId() != null) {
            this.hadIdCounter.increment();
            this.auditEventRepository.add(
                new AuditEvent("me",
                    "hadIdEvent",
                    "hadId="+todo.getId()));
        }

        LOG.trace("creating Todo " + todo);
        LOG.debug("creating Todo " + todo);
        LOG.info("creating Todo " + todo);
        LOG.warn("creating Todo " + todo);
        LOG.error("creating Todo " + todo);

        todo.setId(seq++);
        todos.put(todo.getId(), todo);
        return todos.get(todo.getId());
    }

    public Todo altCreateTodo(Todo todo) {
        this.altCreateCounter.increment();
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
        return old;
    }

    @GetMapping("/limit")
    public Integer getLimit() {
        return config.getLimit();
    }

}
