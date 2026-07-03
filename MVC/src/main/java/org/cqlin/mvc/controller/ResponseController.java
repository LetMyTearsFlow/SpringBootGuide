package org.cqlin.mvc.controller;

import org.cqlin.mvc.pojo.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ResponseController {

    @RequestMapping("/response01")
    public Profile response01() {
        Profile profile = new Profile();
        profile.setMessage("真有你的");
        profile.setUsername("青哥");
        return profile;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() throws IOException {
        InputStream stream = Files.newInputStream(Paths.get("C:\\Users\\78218\\Pictures\\homelander.jpg"));
        byte[] resource = stream.readAllBytes();
        String fileName = URLEncoder.encode("祖国人.jpg", StandardCharsets.UTF_8);
        System.out.println(fileName);
        System.out.println(resource.length);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename*=UTF-8''" + fileName)                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.length)
                .body(resource);
    }
}
