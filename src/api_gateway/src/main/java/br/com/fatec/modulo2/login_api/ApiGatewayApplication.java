package br.com.fatec.modulo2.login_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @GetMapping("/")
    public Map<String, Object> info() {
        return Map.of(
                "service", "API Gateway",
                "version", "1.0.0",
                "description", "Gateway de roteamento para microserviços",
                "availableRoutes", Map.of(
                        "publicRoutes", List.of(
                                Map.of(
                                        "path", "/api/login",
                                        "method", "POST",
                                        "auth", "Não requerida",
                                        "description", "Autenticação de usuário",
                                        "body", Map.of(
                                                "username", "string",
                                                "password", "string"
                                        ),
                                        "response", "Bearer Token"
                                ),
                                Map.of(
                                        "path", "/api/register",
                                        "method", "POST",
                                        "auth", "Não requerida",
                                        "description", "Registro de novo usuário",
                                        "body", Map.of(
                                                "username", "string",
                                                "password", "string (mín. 6 caracteres)"
                                        ),
                                        "response", "Mensagem de sucesso"
                                ),
                                Map.of(
                                        "path", "/api/health",
                                        "method", "GET",
                                        "auth", "Não requerida",
                                        "description", "Status do serviço de login",
                                        "response", "Status UP/DOWN"
                                ),
                                Map.of(
                                        "path", "/health",
                                        "method", "GET",
                                        "auth", "Não requerida",
                                        "description", "Status do gateway",
                                        "response", "Status e rotas disponíveis"
                                )
                        ),
                        "protectedRoutes", List.of(
                                Map.of(
                                        "path", "/api/user/profile",
                                        "method", "GET",
                                        "auth", "Bearer Token requerido",
                                        "description", "Obter perfil do usuário autenticado",
                                        "header", "Authorization: Bearer <token>",
                                        "response", "Dados do perfil"
                                ),
                                Map.of(
                                        "path", "/api/user/secret",
                                        "method", "GET",
                                        "auth", "Bearer Token requerido",
                                        "description", "Acessar dados secretos protegidos",
                                        "header", "Authorization: Bearer <token>",
                                        "response", "Dados secretos"
                                ),
                                Map.of(
                                        "path", "/api/user/update",
                                        "method", "PUT",
                                        "auth", "Bearer Token requerido",
                                        "description", "Atualizar dados do usuário",
                                        "header", "Authorization: Bearer <token>",
                                        "body", "JSON com campos a atualizar",
                                        "response", "Confirmação de atualização"
                                )
                        )
                ),
                "usage", Map.of(
                        "login", "curl -X POST http://localhost:8080/api/login -H 'Content-Type: application/json' -d '{\"username\":\"admin\",\"password\":\"admin123\"}'",
                        "protectedRoute", "curl http://localhost:8080/api/user/profile -H 'Authorization: Bearer <seu-token>'"
                )
        );
    }
}