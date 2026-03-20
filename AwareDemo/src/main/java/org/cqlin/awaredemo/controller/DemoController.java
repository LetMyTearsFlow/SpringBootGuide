package org.cqlin.awaredemo.controller;

import org.cqlin.awaredemo.service.AwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {
    @Autowired
    private AwareService awareService;

    @GetMapping("/aware/info")
    public Map<String, Object> info() {
        return awareService.getAwareInfo();
    }

    @GetMapping("/aware/context")
    public String context() {
        return awareService.getBeanByContext();
    }
}
