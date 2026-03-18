package org.cqlin.resourceautowired.controller;

import jakarta.annotation.Resource;
import org.cqlin.resourceautowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // ===============================
    // 1. @Autowired 按类型注入（会报错）
    // ===============================
    // 因为有两个 UserService 实现
    // 会报：NoUniqueBeanDefinitionException
    /*
    @Autowired
    private UserService userService;
    */

    // ===============================
    // 2. @Autowired + @Qualifier（解决冲突）
    // ===============================
    @Autowired
    @Qualifier("userServiceImpl1")
    private UserService userService1;

    @GetMapping("/autowired/qualifier")
    public String testAutowiredQualifier() {
        return userService1.getUser();
    }

    // ===============================
    // 3. @Resource 按名称注入（成功）
    // ===============================
    @Resource(name = "userServiceImpl2")
    private UserService userService2;

    @GetMapping("/resource/name")
    public String testResourceByName() {
        return userService2.getUser();
    }

    // ===============================
    // 4. @Resource 默认按字段名注入
    // ===============================
    @Resource
    private UserService userServiceImpl1; // 字段名 = bean 名

    @GetMapping("/resource/default")
    public String testResourceDefault() {
        return userServiceImpl1.getUser();
    }

    // ===============================
    // 5. @Autowired(required = false)
    // ===============================
    @Autowired(required = false)
    @Qualifier("notExistBean")
    private UserService optionalService;

    @GetMapping("/autowired/required")
    public String testRequired() {
        return optionalService == null ? "NULL (no bean found)" : optionalService.getUser();
    }

}