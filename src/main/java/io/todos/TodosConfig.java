package io.todos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class TodosConfig {

    @Value("${todos.limit}")
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }
}
