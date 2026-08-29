package org.cqlin.datavalidationdemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "{user.address.province.not-blank}")
        String province,

        @NotBlank(message = "{user.address.city.not-blank}")
        String city,

        @Size(max = 200, message = "{user.address.detail.size}")
        String detail
) {
}
