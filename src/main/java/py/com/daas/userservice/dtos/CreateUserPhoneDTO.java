package py.com.daas.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserPhoneDTO(
        @Schema(example = "1234567") String number,
        @Schema(example = "1") Long cityCode,
        @Schema(example = "57") Long countryCode
) {}
