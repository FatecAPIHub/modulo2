package br.com.fatec.modulo2.login_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO (Data Transfer Object) para requisição de login
 *
 * Utilizando Java Record para imutabilidade e concisão
 *
 * Exemplo de JSON esperado:
 * {
 *   "username": "admin",
 *   "password": "admin123"
 * }
 */
public record LoginRequest(
        @JsonProperty("username")
        String username,

        @JsonProperty("password")
        String password
) {
    /**
     * Construtor compacto com validações básicas
     */
    public LoginRequest {
        if (username != null) {
            username = username.trim();
        }
        if (password != null) {
            password = password.trim();
        }
    }

    /**
     * Verifica se os campos são válidos
     *
     * @return true se username e password não são nulos/vazios
     */
    public boolean isValid() {
        return username != null && !username.isBlank() &&
                password != null && !password.isBlank();
    }
}