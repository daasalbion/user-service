package py.com.daas.userservice.factories;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import py.com.daas.userservice.dtos.CreateUserPhoneDTO;
import py.com.daas.userservice.dtos.CreateUserDTO;
import py.com.daas.userservice.dtos.UserDTO;
import py.com.daas.userservice.models.User;
import py.com.daas.userservice.models.UserPhoneNumber;

public class UserTestFactory {
    private final LocalDateTime now = LocalDateTime.now();
    private final Boolean active = true;
    private final String email = "derlisarguello@gmail.com";
    private final String name = "Derlis Arguello";
    private final String password = "$DGrTf%NP)8c$t6!";
    private final String token = "t0k3n";
    private final UUID publicUid = UUID.randomUUID();

    private final Set<CreateUserPhoneDTO> phones = Set.of(
            new CreateUserPhoneDTO("1234", 2L, 34L),
            new CreateUserPhoneDTO("555", 31L, 21L)
    );

    private final Set<UserPhoneNumber> userPhones = phones
            .stream()
            .map(p -> new UserPhoneNumber(p.number(), p.cityCode(), p.countryCode()))
            .collect(Collectors.toSet());

    public User getUser() {
        var user = new User(name, email, password, token,  userPhones);
        Long id = 1L;
        user.setId(id);
        user.setIsActive(active);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setLastLogin(now);
        user.setPublicUid(publicUid);
        return user;
    }

    public CreateUserDTO getCreateUserDTO() {
        return new CreateUserDTO(name, email, password, phones);
    }

    public UserDTO getUserDTO() {
        return new UserDTO(publicUid, now, now, now, token, active);
    }
}
