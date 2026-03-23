package org.cqlin.resourceutilsdemo.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class ResourceService {
    /**
     * 使用 ResourceUtils 读取 classpath 文件，并返回文件内容
     * 注意：这种方式在 IDE 开发环境通常没问题，
     * 但打成 jar 后，classpath 资源未必能转成 File。实测打包之后再运行就报错了。
     */
    public String readClasspathByResourceUtils() throws IOException {
        File file = ResourceUtils.getFile("classpath:test.txt");
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * 使用 ClassPathResource 读取 classpath 文件
     * 推荐方式：兼容 jar 包运行
     */
    public String readClassPathByClassPathResource() throws IOException {
        ClassPathResource resource = new ClassPathResource("test.txt");
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * 使用 ResourceUtils 读取本地文件
     */
    public String readLocalFile(String path) throws IOException {
        File file = ResourceUtils.getFile(path);
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * 演示 ResourceUtils.isUrl
     */
    public boolean isUrl(String path) {
        return ResourceUtils.isUrl(path);
    }

    /**
     * 演示获取 URL
     */
    public String getUrl(String location) throws FileNotFoundException {
        return ResourceUtils.getURL(location).toString();
    }
}
