package py.com.daas.userservice.services;

public interface JwtService {
    String generateToken(String username);
}
