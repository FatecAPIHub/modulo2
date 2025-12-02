package br.com.fatec.modulo2.login_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * DTO (Data Transfer Object) para resposta de login
 *
 * Utilizando Java Record para imutabilidade
 *
 * Exemplo de JSON retornado:
 * {
 *   "token": "eyJhbGciOiJIUzI1NiJ9...",
 *   "type": "Bearer",
 *   "expiresIn": 86400000,
 *   "issuedAt": "2024-12-01T10:30:00"
 * }
 */
public record LoginResponse(
        @JsonProperty("token")
        String token,

        @JsonProperty("type")
        String type,

        @JsonProperty("expiresIn")
        Long expiresIn,

        @JsonProperty("issuedAt")
        String issuedAt
) {
    /**
     * Construtor simplificado - apenas token
     * Define valores padrão para os outros campos
     */
    public LoginResponse(String token) {
        this(token, "Bearer", 86400000L, getCurrentTimestamp());
    }

    /**
     * Construtor com token e tempo de expiração customizado
     */
    public LoginResponse(String token, Long expiresIn) {
        this(token, "Bearer", expiresIn, getCurrentTimestamp());
    }

    /**
     * Obtém o timestamp atual formatado
     */
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().toString();
    }

    /**
     * Calcula a data/hora de expiração
     */
    public String getExpirationTime() {
        if (expiresIn == null) return null;

        Instant expirationInstant = Instant.now().plusMillis(expiresIn);
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(
                expirationInstant,
                ZoneId.systemDefault()
        );

        return expirationDateTime.toString();
    }
}
