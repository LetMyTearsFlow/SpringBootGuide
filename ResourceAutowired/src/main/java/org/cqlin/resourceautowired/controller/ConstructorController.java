package org.cqlin.resourceautowired.controller;

import org.cqlin.resourceautowired.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorController {
    private final UserService userService;

    // Spring 4.3+ 可以省略 @Autowired
    public ConstructorController(@Qualifier("userServiceImpl1") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/constructor")
    public String testConstructor() {
        return userService.getUser();
    }
}
