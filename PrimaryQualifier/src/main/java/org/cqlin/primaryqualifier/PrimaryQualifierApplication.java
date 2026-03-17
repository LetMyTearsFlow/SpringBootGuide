package org.cqlin.primaryqualifier;

import org.cqlin.primaryqualifier.controller.MessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PrimaryQualifierApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(PrimaryQualifierApplication.class, args);
        MessageController controller = ioc.getBean(MessageController.class);
        controller.send();
    }

}
