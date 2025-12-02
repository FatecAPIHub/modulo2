package br.com.fatec.modulo2.login_api.controller;

import br.com.fatec.modulo2.login_api.dto.LoginRequest;
import br.com.fatec.modulo2.login_api.dto.LoginResponse;
import br.com.fatec.modulo2.login_api.service.AuthService;
import br.com.fatec.modulo2.login_api.service.JwtService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthService authService;
    private final JwtService jwtService;

    public LoginController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint de login
     * POST /login
     * Body: { "username": "admin", "password": "admin123" }
     *
     * Retorna:
     * - 200 OK: { "token": "eyJhbGc...", "type": "Bearer" }
     * - 401 UNAUTHORIZED: { "error": "Credenciais inválidas" }
     * - 400 BAD REQUEST: { "error": "Username e password são obrigatórios" }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Validação básica
        if (!request.isValid()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username e password são obrigatórios"));
        }

        // Autenticação
        if (authService.authenticate(request.username(), request.password())) {
            String token = jwtService.generateToken(request.username());
            return ResponseEntity.ok(new LoginResponse(token));
        }

        // Credenciais inválidas
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciais inválidas"));
    }
}