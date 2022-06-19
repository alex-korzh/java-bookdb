package name.dezalator.bookdb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
public class AuthUtils {
    private String token;
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private DecodedJWT decodedJWT;
    private String userEmail;


    public AuthUtils(@Value("${bookdb.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public void initAuth(String authHeader) {
        token = authHeader.substring("Bearer ".length());
        verifier = JWT.require(algorithm).build();
        decodedJWT = verifier.verify(token);
        userEmail = decodedJWT.getSubject();
    }

    private void doAuth() {
        String role = decodedJWT.getClaim("role").asString();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public Map<String, String> refresh(String dbEmail, String role, String uri) {
        String accessToken = generateAccessToken(dbEmail, role, uri);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", token);
        return tokens;
    }

    public Map<String, String> generateTokens(String email, String role, String uri) {
        String accessToken = generateAccessToken(email, role, uri);
        String refreshToken = generateRefreshToken(email, uri);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    private String generateAccessToken(String email, String role, String uri) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
                .withIssuer(uri)
                .withClaim("role", role)
                .sign(algorithm);
    }

    private String generateRefreshToken(String email, String uri) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000 * 60 * 1000))
                .withIssuer(uri)
                .sign(algorithm);
    }

    public void authorize(String authHeader) {
        initAuth(authHeader);
        doAuth();
    }

}
