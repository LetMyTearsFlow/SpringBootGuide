package org.cqlin.mybatisbasicdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
