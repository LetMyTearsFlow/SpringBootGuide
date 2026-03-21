package org.cqlin.valuedemo.controller;

import org.cqlin.valuedemo.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    // 1. 字符串注入固定值
    @Value("Hello @Value")
    private String directValue;

    @Value("67")
    private int directNumber;

    // 2. 从配置文件中读取
    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private int userAge;

    @Value("${user.city}")
    private String userCity;

    @Value("${app.title}")
    private String appTitle;

    // 配置不存在时可设置默认值
    @Value("${user.gender:男}")
    private String gender;

    // 3. 使用SpEL
    // 3.1 数学运算
    @Value("#{10 + 20}")
    private int sum;

    // 3.2 静态方法用T表示类型
    @Value("#{T(java.lang.Math).random()}")
    private double randomValue;

    // 3.3 静态常量
    @Value("#{T(java.lang.Integer).MAX_VALUE}")
    private int maxInt;

    // 3.4 字符串转大写
    @Value("#{'spring boot'.toUpperCase()}")
    private String upperCaseStr;

    // 3.5 条件表达式
    @Value("#{${user.age} >= 18 ? '成年人' : '未成年人'}")
    private String ageType;

    // 3.6 从配置读取后继续计算
    @Value("#{${user.age} + 10}")
    private int ageAfter10Years;

    // 3.7 注入 List
    @Value("#{'${app.tags}'.split(',')}")
    private List<String> tagList;

    // 3.8 注入 Map
    @Value("#{${student.info}}")
    private Map<String, Object> studentInfo;

    // 3.9 引用 Bean 属性
    @Value("#{userBean.nickname}")
    private String beanNickname;

    // 3.10 调用 Bean 方法
    @Value("#{userBean.sayHello()}")
    private String beanHello;

    // 3.11 布尔值判断
    @Value("#{${feature.enabled} ? '功能已开启' : '功能未开启'}")
    private String featureStatus;

    @Autowired
    private UserBean userBean;

    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();

        map.put("1_directValue", directValue);
        map.put("2_directNumber", directNumber);
        map.put("3_userName", userName);
        map.put("4_userAge", userAge);
        map.put("5_userCity", userCity);
        map.put("6_appTitle", appTitle);
        map.put("7_gender_default", gender);

        map.put("8_sum", sum);
        map.put("9_randomValue", randomValue);
        map.put("10_maxInt", maxInt);
        map.put("11_upperCaseStr", upperCaseStr);
        map.put("12_ageType", ageType);
        map.put("13_ageAfter10Years", ageAfter10Years);
        map.put("14_tagList", tagList);
        map.put("15_studentInfo", studentInfo);
        map.put("16_beanNickname", beanNickname);
        map.put("17_beanHello", beanHello);
        map.put("18_featureStatus", featureStatus);
        map.put("19_userBeanDirectCall", userBean.sayHello());

        return map;
    }
}
