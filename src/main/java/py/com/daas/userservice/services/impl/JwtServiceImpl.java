package py.com.daas.userservice.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import py.com.daas.userservice.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${token.signature.algorithm}")
    private SignatureAlgorithm signatureAlgorithm;

    @Value("${token.expiration.millis}")
    private Long tokenExpirationMillis;

    @Value("${token.secret.key}")
    private String secretKey;

    @Override
    public String generateToken(String username) {
        var issuedAt = new Date();
        var expiresAt = new Date(issuedAt.getTime() + tokenExpirationMillis);

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(signatureAlgorithm, secretKey.getBytes())
                .compact();

    }
}
