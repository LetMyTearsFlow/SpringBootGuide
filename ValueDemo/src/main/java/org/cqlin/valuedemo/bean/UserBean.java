package org.cqlin.valuedemo.bean;

import org.springframework.stereotype.Component;

@Component
public class UserBean {
    private String nickname = "小明";

    public String getNickname() {
        return nickname;
    }

    public String sayHello() {
        return "你好，我是 " + nickname;
    }
}
