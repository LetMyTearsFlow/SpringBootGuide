package org.cqlin.datavalidationdemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.cqlin.datavalidationdemo.validation.ValidPhone;

public record UserCreateRequest(
        @NotBlank(message = "{user.username.not-blank}")
        @Size(min = 3, max = 20, message = "{user.username.size}")
        String username,

        @NotBlank(message = "{user.email.not-blank}")
        @Email(message = "{user.email.invalid}")
        String email,

        @NotNull(message = "{user.age.not-null}")
        @Min(value = 18, message = "{user.age.min}")
        @Max(value = 120, message = "{user.age.max}")
        Integer age,

        @NotBlank(message = "{user.password.not-blank}")
        @Size(min = 8, max = 32, message = "{user.password.size}")
        String password,

        @NotBlank(message = "{user.phone.not-blank}")
        @ValidPhone
        String phone
) {
}
