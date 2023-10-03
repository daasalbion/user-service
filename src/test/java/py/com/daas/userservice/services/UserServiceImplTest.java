package py.com.daas.userservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import py.com.daas.userservice.exceptions.EmailExistsException;
import py.com.daas.userservice.factories.UserTestFactory;
import py.com.daas.userservice.repositories.UserRepository;
import py.com.daas.userservice.services.impl.UserServiceImpl;

class UserServiceImplTest {
    private final JwtService mockedJwtService = mock(JwtService.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserRepository mockedRepository = mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(passwordEncoder, mockedRepository, mockedJwtService);
    private final UserTestFactory userTestFactory = new UserTestFactory();

    @Test
    void registerUser() {
        var createUserDTO = userTestFactory.getCreateUserDTO();
        var entity = userTestFactory.getUser();
        var expectedDto = userTestFactory.getUserDTO();
        var token = entity.getToken();
        when(mockedRepository.existsByEmailEqualsAndIsActiveIsTrue(createUserDTO.email())).thenReturn(false);
        when(mockedJwtService.generateToken(entity.getEmail())).thenReturn(token);
        when(mockedRepository.save(any())).thenReturn(entity);

        assertThat(userService.create(createUserDTO)).isEqualTo(expectedDto);
    }

    @Test
    void registerThrowsEmailAlreadyExists() {
        var createUserDTO = userTestFactory.getCreateUserDTO();
        when(mockedRepository.existsByEmailEqualsAndIsActiveIsTrue(createUserDTO.email())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(createUserDTO)).isInstanceOf(EmailExistsException.class);
    }
}
