package py.com.daas.userservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import py.com.daas.userservice.dtos.CreateUserDTO;
import py.com.daas.userservice.dtos.UserDTO;
import py.com.daas.userservice.exceptions.EmailExistsException;
import py.com.daas.userservice.repositories.UserRepository;
import py.com.daas.userservice.services.JwtService;
import py.com.daas.userservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {
        validateUniqueEmail(createUserDTO.email());

        var token = jwtService.generateToken(createUserDTO.email());
        var user = UserService.toUser(createUserDTO, token, passwordEncoder);
        var savedUser = userRepository.save(user);

        return UserService.toUserDTO(savedUser);
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmailEqualsAndIsActiveIsTrue(email)) {
            throw new EmailExistsException(email);
        }
    }
}
