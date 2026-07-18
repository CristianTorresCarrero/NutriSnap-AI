package com.nutrisnap.security;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * -------------------------------------------------------
 * Servicio encargado de generar, validar y procesar
 * los JSON Web Tokens (JWT) utilizados para la
 * autenticación de usuarios.
 *
 * Responsabilidades:
 * - Generar Access Tokens.
 * - Validar tokens.
 * - Extraer información del token.
 * - Verificar expiración.
 * -------------------------------------------------------
 */

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * -------------------------------------------------------
     * Genera un Access Token para el usuario.
     * -------------------------------------------------------
     */
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();

    }

    /**
     * Obtiene el correo electrónico almacenado en el token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un dato específico del token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los datos contenidos en el token.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    /**
     * Obtiene la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida que el token pertenezca al usuario y que no haya expirado.
     */
    public boolean isTokenValid(String token, String email) {

        final String username = extractUsername(token);

        return username.equals(email) && !isTokenExpired(token);

    }

}
