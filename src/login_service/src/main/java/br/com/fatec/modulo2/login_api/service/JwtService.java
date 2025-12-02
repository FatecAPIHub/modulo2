package br.com.fatec.modulo2.login_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço responsável pela geração e validação de tokens JWT
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Gera um token JWT para o usuário
     *
     * @param username Nome de usuário
     * @return Token JWT assinado
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        // Cria claims adicionais (opcional)
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("type", "Bearer");

        SecretKey key = getSigningKey();

        return Jwts.builder()
                .subject(username)                    // Subject: identificador do usuário
                .claims(claims)                       // Claims customizados
                .issuedAt(now)                        // Data de criação
                .expiration(expiryDate)               // Data de expiração
                .signWith(key)                        // Assina com a chave secreta
                .compact();
    }

    /**
     * Valida um token JWT
     *
     * @param token Token JWT
     * @return true se o token é válido
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = getSigningKey();
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrai o username do token
     *
     * @param token Token JWT
     * @return Username contido no token
     */
    public String getUsernameFromToken(String token) {
        SecretKey key = getSigningKey();
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Verifica se o token está expirado
     *
     * @param token Token JWT
     * @return true se o token expirou
     */
    public boolean isTokenExpired(String token) {
        try {
            SecretKey key = getSigningKey();
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Obtém a data de expiração do token
     *
     * @param token Token JWT
     * @return Data de expiração
     */
    public Date getExpirationDateFromToken(String token) {
        SecretKey key = getSigningKey();
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration();
    }

    /**
     * Cria a chave de assinatura a partir do secret
     *
     * @return Chave secreta
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}