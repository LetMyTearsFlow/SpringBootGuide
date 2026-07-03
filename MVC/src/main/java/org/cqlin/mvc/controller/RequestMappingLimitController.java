package org.cqlin.mvc.controller;

import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestMappingLimitController {

    // 请求方式限定
    @RequestMapping(value = "/test01", method = {RequestMethod.POST, RequestMethod.PATCH})
    public String test01() {
        return "POST & PATCH method";
    }

    // 请求参数限定
    @RequestMapping(value = "/test02", params = {"username=zhangsan", "ipaddress", "!hengxiang"})
    public String test02() {
        return "Parameters are limited";
    }

    // 请求头限定，如果请求头条件不对报错404
    @RequestMapping(value = "/test03", headers = {"abc", "aaa=123", "!bbb", "ccc!=67"})
    public String test03() {
        return "Headers";
    }

    // 浏览器媒体类型限定
    @RequestMapping(value = "/test04", consumes = "application/json")
    public String test04() {
        return "Media type limit";
    }

    // 返回数据类型确定
    @RequestMapping(value = "/test05", produces = "text/html")
    public String test05() {
        return "<h1>Produce limit<h1>";
    }
}
