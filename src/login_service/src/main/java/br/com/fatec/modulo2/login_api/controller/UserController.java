package br.com.fatec.modulo2.login_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(Map.of(
                "username", username,
                "message", "Perfil do usuário autenticado",
                "authenticated", true
        ));
    }

    @GetMapping("/secret")
    public ResponseEntity<?> getSecretData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(Map.of(
                "username", username,
                "secretData", "Dados super secretos acessíveis apenas com token válido!",
                "timestamp", System.currentTimeMillis()
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(Map.of(
                "message", "Dados atualizados com sucesso",
                "username", username,
                "updatedFields", data.keySet()
        ));
    }
}