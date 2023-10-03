package py.com.daas.userservice.services;

import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;

import py.com.daas.userservice.dtos.CreateUserPhoneDTO;
import py.com.daas.userservice.dtos.CreateUserDTO;
import py.com.daas.userservice.dtos.UserDTO;
import py.com.daas.userservice.models.User;
import py.com.daas.userservice.models.UserPhoneNumber;

public interface UserService {
    UserDTO create(CreateUserDTO createUserDTO);

    static User toUser(CreateUserDTO createUserDTO, String token, PasswordEncoder passwordEncoder) {
        var phones = createUserDTO.phones().stream()
                .map(UserService::toUserPhoneNumber)
                .collect(Collectors.toSet());
        var encodedPassword = passwordEncoder.encode(createUserDTO.password());
        return new User(createUserDTO.name(), createUserDTO.email(), encodedPassword, token, phones);
    }

    static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getPublicUid(), user.getCreatedAt(), user.getUpdatedAt(), user.getLastLogin(),
                user.getToken(), user.getIsActive());
    }

    private static UserPhoneNumber toUserPhoneNumber(CreateUserPhoneDTO createPhoneDTO) {
        return new UserPhoneNumber(createPhoneDTO.number(), createPhoneDTO.cityCode(), createPhoneDTO.countryCode());
    }
}
