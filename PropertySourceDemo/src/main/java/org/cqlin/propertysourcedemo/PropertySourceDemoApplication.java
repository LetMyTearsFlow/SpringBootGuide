package org.cqlin.propertysourcedemo;

import org.cqlin.propertysourcedemo.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PropertySourceDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PropertySourceDemoApplication.class, args);
        UserService userService = context.getBean(UserService.class);
        userService.print();
    }

}
