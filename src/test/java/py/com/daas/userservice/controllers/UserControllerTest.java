package py.com.daas.userservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;

import py.com.daas.userservice.controllers.ExceptionHandlerController.ErrorResponse;
import py.com.daas.userservice.dtos.CreateUserDTO;
import py.com.daas.userservice.exceptions.EmailExistsException;
import py.com.daas.userservice.factories.UserTestFactory;
import py.com.daas.userservice.services.UserService;

@WebMvcTest(excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    private final UserTestFactory userTestFactory = new UserTestFactory();

    @Autowired
    Validator validator;

    @Test
    void testCreateUserOk() throws Exception {
        var createUserDTO = userTestFactory.getCreateUserDTO();
        var userDTO = userTestFactory.getUserDTO();
        var expectedJson = objectMapper.writeValueAsString(userDTO);
        var jsonBody = objectMapper.writeValueAsString(createUserDTO);
        when(userService.create(createUserDTO)).thenReturn(userDTO);
        var response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    void badRequestIfBadEmail() throws Exception {
        var createUserDTO = new CreateUserDTO("John Doe", "notAnEmail", "PasswordStrong30%", Set.of());
        var jsonBody = objectMapper.writeValueAsString(createUserDTO);
        var response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void conflictStatusIfDuplicatedEmail() throws Exception {
        var createUserDTO = userTestFactory.getCreateUserDTO();
        var jsonBody = objectMapper.writeValueAsString(createUserDTO);
        when(userService.create(createUserDTO)).thenThrow(new EmailExistsException(createUserDTO.email()));
        var response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void internalServerErrorWithMessage() throws Exception {
        var createUserDTO = userTestFactory.getCreateUserDTO();
        var jsonBody = objectMapper.writeValueAsString(createUserDTO);
        var errorMessage = "Unexpected error";
        when(userService.create(createUserDTO)).thenThrow(new RuntimeException(errorMessage));
        var response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andReturn()
                .getResponse();
        var parsedResponse = objectMapper.readValue(response.getContentAsString(),
                ErrorResponse.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(parsedResponse.message()).isEqualTo(errorMessage);
    }
}
