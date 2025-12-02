package br.com.fatec.modulo2.login_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;

    // Em produção: use JPA/Hibernate com banco de dados
    private final Map<String, String> users;

    public AuthService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.users = new HashMap<>();


        // Usuários de exemplo
        users.put("admin", passwordEncoder.encode("admin123"));

        users.put("user", passwordEncoder.encode("user123"));

        users.put("test", passwordEncoder.encode("test123"));
    }

    public boolean authenticate(String username, String password) {
        // Verifica se o usuário existe
        if (!users.containsKey(username)) {
            return false;
        }

        String hashedPassword = users.get(username);
        return passwordEncoder.matches(password, hashedPassword);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean registerUser(String username, String password) {
        if (userExists(username)) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);
        users.put(username, hashedPassword);

        return true;
    }
}