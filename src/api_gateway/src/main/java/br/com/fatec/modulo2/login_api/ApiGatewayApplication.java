package br.com.fatec.modulo2.login_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
                        "login", Map.of(
                                "path", "/api/login/login",
                                "method", "POST",
                                "description", "Endpoint de autenticação"
                        ),
                        "health", Map.of(
                                "path", "/health",
                                "method", "GET",
                                "description", "Status do gateway"
                        )
                )
        );
    }
}