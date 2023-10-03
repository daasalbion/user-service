package py.com.daas.userservice.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import py.com.daas.userservice.annotations.EmailPattern;
import py.com.daas.userservice.annotations.PasswordPattern;

public record CreateUserDTO(
        @JsonProperty("nombre")
        @NotEmpty
        @Schema(example = "Juan Rodríguez")
        String name,

        @EmailPattern
        @JsonProperty("correo")
        @NotEmpty
        @Schema(example = "juan@rodriguez.org")
        String email,

        @JsonProperty("contraseña")
        @NotEmpty
        @PasswordPattern
        @Schema(example = "$HGrtf%NCt9!", format = "Debe tener como mínimo 6 caracteres, algunos números y otros " +
                "caracteres especiales!")
        String password,

        @JsonProperty("teléfono")
        Set<CreateUserPhoneDTO> phones
) {}
