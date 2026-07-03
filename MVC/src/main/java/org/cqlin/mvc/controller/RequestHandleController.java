package org.cqlin.mvc.controller;

import org.cqlin.mvc.pojo.EnhancedProfile;
import org.cqlin.mvc.pojo.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RequestHandleController {
    @RequestMapping("/handle01")
    public String handle01(String message) {
        System.out.println(message);
        return "handle01";
    }

    @RequestMapping("/handle02")
    public String handle02(@RequestParam("message") String msg) {
        System.out.println(msg);
        return "handle02";
    }

    @RequestMapping("/handle03")
    public String handle03(@RequestHeader("abc") String abc) {
        System.out.println(abc);
        return "handle03";
    }

    @RequestMapping("/handle04")
    public String handle04(Profile profile) {
        System.out.println(profile.message);
        System.out.println(profile.username);
        return "handle04";
    }

    @RequestMapping("/handle05")
    public String handle05(@CookieValue("preference") String preference) {
        System.out.println(preference);
        return "handle05";
    }

    /**
     * 嵌套属性要写完整路径
     */
    @RequestMapping("/handle06")
    public String handle06(EnhancedProfile profile) {
        System.out.println(profile.date);
        System.out.println(profile.profile.message);
        System.out.println(profile.profile.username);
        return "handle06";
    }

    @RequestMapping("/handle07")
    public String handle07(@RequestBody Profile profile) {
        System.out.println(profile);
        return "handle07";
    }

    @RequestMapping("/handle08")
    public String handle08(@RequestParam("file1") MultipartFile file1,
                           @RequestParam("file2") MultipartFile[] file2) {
        System.out.println(file1.getOriginalFilename());
        System.out.println(file2.length);
        return "handle08";
    }

    @RequestMapping("/handle09")
    public String handle09(HttpEntity<Profile> entity) {
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
        return "handle09";
    }
}
