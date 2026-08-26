package org.cqlin.datavalidationdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataValidationDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies that the Spring application context starts successfully.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Verifies that global validation returns localized Chinese field errors.
     */
    @Test
    void globalValidationReturnsChineseErrors() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "zh-CN")
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data[?(@.field == 'username')].message")
                        .value("用户名长度必须在 3 到 20 个字符之间"))
                .andExpect(jsonPath("$.data[?(@.field == 'phone')].message")
                        .value("手机号格式不正确，必须是 11 位中国大陆手机号"));
    }

    /**
     * Verifies that the same constraints can return English messages.
     */
    @Test
    void globalValidationReturnsEnglishErrors() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "en-US")
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field == 'email')].message")
                        .value("Email format is invalid"));
    }

    /**
     * Verifies the controller-level BindingResult validation path.
     */
    @Test
    void bindingResultEndpointReturnsValidationErrors() throws Exception {
        mockMvc.perform(post("/api/users/binding-result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation failed"));
    }

    /**
     * Verifies that a valid request reaches the controller successfully.
     */
    @Test
    void validRequestReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "spring-user",
                                  "email": "spring@example.com",
                                  "age": 20,
                                  "password": "springboot123",
                                  "phone": "13812345678",
                                  "nickname": "2b",
                                  "score": 67
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * Returns a request body that violates every validation category in the demo.
     */
    private String invalidRequest() {
        return """
                {
                  "username": "a",
                  "email": "not-an-email",
                  "age": 15,
                  "password": "123",
                  "phone": "110"
                }
                """;
    }
}
