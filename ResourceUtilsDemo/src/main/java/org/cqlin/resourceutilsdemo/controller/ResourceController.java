package org.cqlin.resourceutilsdemo.controller;

import org.cqlin.resourceutilsdemo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    @GetMapping("/resourceutils/classpath")
    public Map<String, Object> readClasspathByResourceUtils(){
        Map<String, Object> result = new LinkedHashMap<>();
        try{
            String content = resourceService.readClasspathByResourceUtils();
            result.put("success", true);
            result.put("method", "ResourceUtils.getFile(\"classpath:test.txt\")");
            result.put("content", content);
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", e.getClass().getName());
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/classpathresource")
    public Map<String, Object> readClasspathByClassPathResource() {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String content = resourceService.readClassPathByClassPathResource();
            result.put("success", true);
            result.put("method", "ClassPathResource(\"test.txt\")");
            result.put("content", content);
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", e.getClass().getName());
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 读取本地文件
     * 例如：
     * /local-file?path=/Users/test/demo.txt
     */
    @GetMapping("/local-file")
    public Map<String, Object> readLocalFile(@RequestParam String path) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String content = resourceService.readLocalFile(path);
            result.put("success", true);
            result.put("path", path);
            result.put("content", content);
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", e.getClass().getName());
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 判断路径是否是 URL
     */
    @GetMapping("/is-url")
    public Map<String, Object> isUrl(@RequestParam String path) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("path", path);
        result.put("isUrl", resourceService.isUrl(path));
        return result;
    }

    /**
     * 获取 URL
     */
    @GetMapping("/get-url")
    public Map<String, Object> getUrl(@RequestParam String location) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            result.put("success", true);
            result.put("location", location);
            result.put("url", resourceService.getUrl(location));
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getClass().getName());
            result.put("message", e.getMessage());
        }
        return result;
    }
}
