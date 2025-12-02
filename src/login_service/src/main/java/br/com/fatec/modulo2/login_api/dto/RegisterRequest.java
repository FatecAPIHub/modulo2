package br.com.fatec.modulo2.login_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequest(
        @JsonProperty("username")
        String username,

        @JsonProperty("password")
        String password
) {
    public RegisterRequest {
        if (username != null) {
            username = username.trim();
        }
        if (password != null) {
            password = password.trim();
        }
    }

    public boolean isValid() {
        return username != null && !username.isBlank() &&
                password != null && !password.isBlank() &&
                password.length() >= 6;
    }
}