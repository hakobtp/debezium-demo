package com.debezium.demo;

import com.debezium.demo.course.facade.CourseFacade;
import com.debezium.demo.user.dto.UserDto;
import com.debezium.demo.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DebeziumDemoApplication {

    private final UserFacade userFacade;
    private final CourseFacade courseFacade;

    public static void main(String[] args) {
        SpringApplication.run(DebeziumDemoApplication.class, args);
    }

    @Bean
    @Order(1)
    public CommandLineRunner initUser() {
        return args -> addUsers();
    }

    private void addUsers() {
        List.of(
                        new UserDto().setAge(18).setFirstName("fn1").setLastName("ln1").setEmail("em1@mail.com").setPassword("pass1"),
                        new UserDto().setAge(19).setFirstName("fn2").setLastName("ln2").setEmail("em2@mail.com").setPassword("pass2"),
                        new UserDto().setAge(32).setFirstName("fn3").setLastName("ln3").setEmail("em3@mail.com").setPassword("pass3"),
                        new UserDto().setAge(43).setFirstName("fn4").setLastName("ln4").setEmail("em4@mail.com").setPassword("pass4"))
                .forEach(userFacade::create);
    }
}
