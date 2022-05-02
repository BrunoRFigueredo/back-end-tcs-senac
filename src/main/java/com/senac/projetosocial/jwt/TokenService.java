package com.senac.projetosocial.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TokenService {

    private TokenProperties properties;
    private Algorithm algorithm;
    private TokenKeys tokenKeys;
    private ObjectMapper mapper;

    @Autowired
    public TokenService(TokenProperties properties, TokenKeys tokenKeys, ObjectMapper mapper) {
        this.tokenKeys = tokenKeys;
        this.properties = properties;
        this.mapper = mapper;
        try {
            this.algorithm = this.tokenKeys.getAlgorithm(this.properties.getPublicKey(),
                    this.properties.getPrivateKey());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public String generateToken(UsuarioRepresentation.UsuarioToken user, String roles) {

        LocalDateTime now = LocalDateTime.now();
        return JWT.create()
                .withSubject("logged")
                .withIssuer(this.properties.getIssuer())
                .withIssuedAt(Date
                        .from(now.atZone(ZoneId.systemDefault())
                                .toInstant()))
                .withExpiresAt(Date.from(now.plusSeconds(properties.getMaxAgeSeconds())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .withClaim("user", this.mapper.convertValue(user, Map.class))
                .withClaim("roles", roles)
                .sign(algorithm);
    }

}