package br.com.fatec.modulo2.login_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço responsável pela autenticação de usuários
 *
 * Em produção, substitua o Map por consultas ao banco de dados
 * e use Spring Security com UserDetailsService
 */
@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;

    // Em produção: use JPA/Hibernate com banco de dados
    private final Map<String, String> users;

    public AuthService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.users = new HashMap<>();

        // Senha: admin123
        users.put("admin", passwordEncoder.encode("admin123"));

        // Senha: user123
        users.put("user", passwordEncoder.encode("user123"));

        // Senha: test123
        users.put("test", passwordEncoder.encode("test123"));
    }

    /**
     * Autentica um usuário verificando username e password
     *
     * @param username Nome de usuário
     * @param password Senha em texto plano
     * @return true se as credenciais são válidas, false caso contrário
     */
    public boolean authenticate(String username, String password) {
        // Verifica se o usuário existe
        if (!users.containsKey(username)) {
            return false;
        }

        // Obtém a senha criptografada do usuário
        String hashedPassword = users.get(username);

        // Compara a senha fornecida com a senha criptografada
        return passwordEncoder.matches(password, hashedPassword);
    }

    /**
     * Verifica se um usuário existe
     *
     * @param username Nome de usuário
     * @return true se o usuário existe
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}