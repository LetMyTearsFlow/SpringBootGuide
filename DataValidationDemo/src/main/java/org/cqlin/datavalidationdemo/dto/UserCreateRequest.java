package org.cqlin.datavalidationdemo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
        String phone,

        @Size(min = 2, max = 30, message="{user.nickname.length}")
        String nickname,

        @NotNull(message="{user.score.not-null}")
        @Min(value = 0, message="{user.score.min}")
        @Max(value = 100, message="{user.score.max}")
        Integer score,

        @Pattern(regexp="\\d{17}[0-9xX]", message="{user.idCard.pattern}")
        String idCard,

        @Valid
        @NotNull(message = "{user.address.not-null}")
        AddressRequest address
) {
}
