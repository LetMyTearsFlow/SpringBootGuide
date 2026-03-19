package org.cqlin.injectiondemo.controller;

import org.cqlin.injectiondemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(@RequestParam(defaultValue = "张三") String name) {
        return userService.registerUser(name);
    }

    @GetMapping("/notify")
    public String notify(@RequestParam(defaultValue = "李四") String name) {
        return userService.notifyUser(name);
    }
}
