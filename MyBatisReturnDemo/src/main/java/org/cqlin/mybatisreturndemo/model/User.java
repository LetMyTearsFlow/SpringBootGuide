package org.cqlin.mybatisreturndemo.model;

public class User {
    private Long id;
    private String userName;
    private Integer age;

    // 提供 MyBatis 创建对象所需的无参构造。
    public User() {
    }

    // 获取用户主键。
    public Long getId() {
        return id;
    }

    // 设置用户主键。
    public void setId(Long id) {
        this.id = id;
    }

    // 获取用户名。
    public String getUserName() {
        return userName;
    }

    // 设置用户名。
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // 获取年龄，允许数据库中的 NULL。
    public Integer getAge() {
        return age;
    }

    // 设置年龄。
    public void setAge(Integer age) {
        this.age = age;
    }
}
