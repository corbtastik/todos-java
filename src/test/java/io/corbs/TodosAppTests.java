package io.corbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TodosAppTests {

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void createDelete() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("[]");

        Todo todo = Todo.builder().title("unit test create todo").build();
        Todo createdTodo = this.restTemplate
            .postForObject("/", todo, Todo.class);

        assertThat(createdTodo.getId()).isGreaterThanOrEqualTo(0);
        assertThat(createdTodo.getTitle()).isEqualTo("unit test create todo");
        assertThat(createdTodo.isCompleted()).isFalse();

        this.restTemplate.delete("/" + createdTodo.getId());
        body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("[]");
    }
}


