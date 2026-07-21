package org.cqlin.marketdemo.controller;

import org.cqlin.marketdemo.common.Result;
import org.cqlin.marketdemo.entity.User;
import org.cqlin.marketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        return Result.success(userService.getUser(id));
    }
}
