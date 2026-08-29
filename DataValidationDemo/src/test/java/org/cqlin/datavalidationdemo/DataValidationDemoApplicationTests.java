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

    private static final String GLOBAL_VALIDATION_URL = "/api/users";
    private static final String BINDING_RESULT_URL = "/api/users/binding-result";
    private static final String VALID_ID_CARD_PREFIX = "11010519491231002";

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
        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "zh-CN")
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data[?(@.field == 'username')].message")
                        .value("用户名长度必须在 3 到 20 个字符之间"))
                .andExpect(jsonPath("$.data[?(@.field == 'phone')].message")
                        .value("手机号格式不正确，必须是 11 位中国大陆手机号"))
                .andExpect(jsonPath("$.data[?(@.field == 'idCard')].message")
                        .value("身份证号必须是18位，前17位必须是数字，最后一位必须是数字，小写x或者大写X"));
    }

    /**
     * Verifies that the same constraints can return English messages.
     */
    @Test
    void globalValidationReturnsEnglishErrors() throws Exception {
        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "en-US")
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field == 'email')].message")
                        .value("Email format is invalid"))
                .andExpect(jsonPath("$.data[?(@.field == 'idCard')].message")
                        .value("The ID number must be 18 characters long; the first 17 characters must be digits, and the last character must be a digit or the letter 'x' (lowercase or uppercase)."));
    }

    /**
     * Verifies the controller-level BindingResult validation path.
     */
    @Test
    void bindingResultEndpointReturnsValidationErrors() throws Exception {
        mockMvc.perform(post(BINDING_RESULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation failed"))
                .andExpect(jsonPath("$.data[?(@.field == 'idCard')]").isNotEmpty());
    }

    /**
     * Verifies that a valid request reaches the controller successfully.
     */
    @Test
    void validRequestReturnsSuccess() throws Exception {
        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequest("2b", 67, VALID_ID_CARD_PREFIX + "X")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"));
    }

    /**
     * Verifies that nickname accepts null and both valid length boundaries.
     */
    @Test
    void nicknameAcceptsNullAndValidLengthBoundaries() throws Exception {
        String[] validNicknames = {null, "ab", "n".repeat(30)};

        for (String nickname : validNicknames) {
            mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validRequest(nickname, 67, VALID_ID_CARD_PREFIX + "X")))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Verifies that nickname rejects values outside its configured length range.
     */
    @Test
    void nicknameRejectsInvalidLengthBoundaries() throws Exception {
        String[] invalidNicknames = {"a", "n".repeat(31)};

        for (String nickname : invalidNicknames) {
            mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Accept-Language", "zh-CN")
                            .content(validRequest(nickname, 67, VALID_ID_CARD_PREFIX + "X")))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[?(@.field == 'nickname')].message")
                            .value("昵称长度必须在 2 到 30 个字符之间"));
        }
    }

    /**
     * Verifies that score accepts its minimum and maximum boundary values.
     */
    @Test
    void scoreAcceptsValidBoundaryValues() throws Exception {
        int[] validScores = {0, 100};

        for (int score : validScores) {
            mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validRequest("2b", score, VALID_ID_CARD_PREFIX + "X")))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Verifies that score rejects values below and above its configured range.
     */
    @Test
    void scoreRejectsInvalidBoundaryValues() throws Exception {
        int[] invalidScores = {-1, 101};
        String[] expectedMessages = {"分数不能小于 0", "分数不能大于 100"};

        for (int index = 0; index < invalidScores.length; index++) {
            mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Accept-Language", "zh-CN")
                            .content(validRequest("2b", invalidScores[index], VALID_ID_CARD_PREFIX + "X")))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data[?(@.field == 'score')].message")
                            .value(expectedMessages[index]));
        }
    }

    /**
     * Verifies that a missing score uses the configured English required message.
     */
    @Test
    void missingScoreReturnsEnglishRequiredMessage() throws Exception {
        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "en-US")
                        .content(validRequest("2b", null, VALID_ID_CARD_PREFIX + "X")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field == 'score')].message")
                        .value("Score must not be null"));
    }

    /**
     * Verifies every allowed final character in the simplified ID card pattern.
     */
    @Test
    void idCardAcceptsDigitAndLetterEndings() throws Exception {
        String[] validIdCards = {
                VALID_ID_CARD_PREFIX + "0",
                VALID_ID_CARD_PREFIX + "X",
                VALID_ID_CARD_PREFIX + "x"
        };

        for (String idCard : validIdCards) {
            mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validRequest("2b", 67, idCard)))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Verifies that an unsupported ID card final character is rejected.
     */
    @Test
    void idCardRejectsUnsupportedFinalCharacter() throws Exception {
        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "zh-CN")
                        .content(validRequest("2b", 67, VALID_ID_CARD_PREFIX + "A")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field == 'idCard')].message")
                        .value("身份证号必须是18位，前17位必须是数字，最后一位必须是数字，小写x或者大写X"));
    }

    /**
     * Verifies that cascaded validation returns the complete nested field path.
     */
    @Test
    void nestedAddressCityValidationReturnsFullFieldPath() throws Exception {
        String request = requestWithAddress(
                "2b",
                67,
                VALID_ID_CARD_PREFIX + "X",
                "Fujian",
                "",
                "No. 1 Example Road");

        mockMvc.perform(post(GLOBAL_VALIDATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "zh-CN")
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[?(@.field == 'address.city')].field")
                        .value("address.city"))
                .andExpect(jsonPath("$.data[?(@.field == 'address.city')].message")
                        .value("地址的城市不能为空"));
    }

    /**
     * Builds a valid request while allowing one exercise field to vary per test.
     */
    private String validRequest(String nickname, Integer score, String idCard) {
        return requestWithAddress(
                nickname,
                score,
                idCard,
                "Fujian",
                "Putian",
                "No. 1 Example Road");
    }

    /**
     * Builds a request with configurable nested address fields.
     */
    private String requestWithAddress(
            String nickname,
            Integer score,
            String idCard,
            String province,
            String city,
            String detail) {
        String nicknameJson = nickname == null ? "null" : "\"" + nickname + "\"";
        String scoreJson = score == null ? "null" : score.toString();

        return """
                {
                  "username": "spring-user",
                  "email": "spring@example.com",
                  "age": 20,
                  "password": "springboot123",
                  "phone": "13812345678",
                  "nickname": %s,
                  "score": %s,
                  "idCard": "%s",
                  "address": {
                    "province": "%s",
                    "city": "%s",
                    "detail": "%s"
                  }
                }
                """.formatted(
                        nicknameJson,
                        scoreJson,
                        idCard,
                        province,
                        city,
                        detail);
    }

    /**
     * Returns a request body that violates the validation rules used by the demo.
     */
    private String invalidRequest() {
        return """
                {
                  "username": "a",
                  "email": "not-an-email",
                  "age": 15,
                  "password": "123",
                  "phone": "110",
                  "nickname": "a",
                  "score": -1,
                  "idCard": "11010519491231002A"
                }
                """;
    }
}
