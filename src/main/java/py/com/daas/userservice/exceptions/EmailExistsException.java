package py.com.daas.userservice.exceptions;

import lombok.Getter;

@Getter
public class EmailExistsException extends RuntimeException {
    private final String message;

    public EmailExistsException() {
        this.message = "El correo ya está registrado";
    }

    public EmailExistsException(String email) {
        this.message = String.format("El correo %s ya está registrado", email);
    }

}
