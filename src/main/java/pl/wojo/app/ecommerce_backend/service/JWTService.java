package pl.wojo.app.ecommerce_backend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Service
public class JWTService {
    private JWT jwt;

    private long expiresInSeconds = 60 * 60 * 1000; // 1h

    private Date current_date;

    private Algorithm algorithm;

    @Value("jwt.algorithm.secret_key")  // 1.
    private String secret_key; 

    @PostConstruct // 2.
    public void postConstruct() {
        current_date = new Date();
        algorithm = Algorithm.HMAC256(secret_key); // not sure;
    }

    public String generateJWT(LocalUser user) {
        String JWT_token = jwt.create()
            .withClaim("sub", user.getId()) // subject
            .withIssuedAt(current_date)
            //TODO: roles
            .withExpiresAt(new Date(current_date.getTime() + expiresInSeconds))
            .sign(algorithm);
            
        return JWT_token;
    }
}
