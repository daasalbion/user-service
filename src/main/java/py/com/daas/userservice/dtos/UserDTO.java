package py.com.daas.userservice.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
    UUID id,
    LocalDateTime created,
    LocalDateTime modified,
    LocalDateTime lastLogin,
    String token,
    boolean isActive
) {}
