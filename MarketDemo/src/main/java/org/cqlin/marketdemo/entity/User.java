package org.cqlin.marketdemo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    public Long id;
    public String username;
    public String password;
    public String phone;
    public LocalDateTime create_time;
}
