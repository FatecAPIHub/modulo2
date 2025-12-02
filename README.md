# 🔐 Sistema de Autenticação com API Gateway

Sistema completo de autenticação JWT (JSON Web Token) implementado com Spring Boot 3, Java 21 e arquitetura de microserviços utilizando API Gateway.

---

## 🎯 Sobre o Projeto

Sistema de autenticação baseado em JWT (JSON Web Token) que implementa:

- ✅ **Login com Bearer Token**: Autenticação de usuários retornando token JWT
- ✅ **Registro de usuários**: Cadastro de novos usuários com senha criptografada (BCrypt)
- ✅ **Rotas protegidas**: Endpoints que requerem token válido para acesso
- ✅ **API Gateway**: Ponto único de entrada para os microserviços
- ✅ **Isolamento de containers**: Login Service não expõe porta diretamente ao host
- ✅ **Dockerização completa**: Aplicação totalmente containerizada

---

## ✅ Requisitos Atendidos

### 🔑 1. Autenticação com Bearer Token

O sistema retorna um **Bearer Token JWT** válido ao enviar credenciais corretas através do endpoint `/api/login`:

```bash
POST /api/login
{
  "username": "admin",
  "password": "admin123"
}

# Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "expiresIn": 86400000,
  "issuedAt": "2024-12-01T10:30:00"
}
```

### 🌐 2. API Gateway

✅ **Implementado** - Todas as requisições passam obrigatoriamente pelo API Gateway na porta `8080`. O Login Service não é acessível diretamente.

```
Cliente → API Gateway (8080) → Login Service (8081 - interna)
```

### 🔒 3. Isolamento do Container

✅ **Implementado** - O container `login-service` **NÃO expõe porta** para o host:

```yaml
# docker-compose.yml
login-service:
  expose:
    - "8081"  # Apenas para rede interna
  # SEM "ports:" - não acessível externamente
```

**Teste:**
```bash
# ❌ Falha - porta não exposta
curl http://localhost:8081/login

# ✅ Sucesso - através do Gateway
curl http://localhost:8080/api/login
```

### 🐳 4. Dockerfiles

✅ **Implementado** - Dockerfiles multi-stage para ambos os serviços:

- `login-service/Dockerfile` - Build e runtime otimizados
- `api-gateway/Dockerfile` - Build e runtime otimizados
- `docker-compose.yml` - Orquestração completa

---

## 🛠️ Tecnologias Utilizadas

### Backend

- **Java 21** - Última versão LTS
- **Spring Boot 3.4.0** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Cloud Gateway** - API Gateway reativo
- **JJWT 0.12.3** - Geração e validação de tokens JWT
- **BCrypt** - Criptografia de senhas

### DevOps

- **Docker** - Containerização
- **Docker Compose** - Orquestração de containers
- **Maven** - Gerenciamento de dependências

### Arquitetura

- **Microserviços** - Arquitetura distribuída
- **API Gateway Pattern** - Ponto único de entrada
- **JWT Stateless Authentication** - Autenticação sem estado

---

## 🏗️ Arquitetura

### Diagrama de Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                       CLIENTE                            │
│            (Navegador, Postman, cURL, etc)              │
└────────────────────┬────────────────────────────────────┘
                     │
                     │ HTTP Request
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                   API GATEWAY                            │
│               (Porta 8080 - Pública)                     │
│                                                          │
│  Responsabilidades:                                      │
│  ✓ Roteamento de requisições                           │
│  ✓ Ponto único de entrada                              │
│  ✓ CORS configurado                                     │
│  ✓ Reescrita de URLs                                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     │ Rede Docker Interna
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                  LOGIN SERVICE                           │
│             (Porta 8081 - Interna apenas)                │
│                                                          │
│  Responsabilidades:                                      │
│  ✓ Autenticação de usuários                            │
│  ✓ Geração de tokens JWT                               │
│  ✓ Validação de tokens                                 │
│  ✓ Registro de usuários                                │
│  ✓ Rotas protegidas                                     │
└─────────────────────────────────────────────────────────┘
```

### Fluxo de Autenticação

```
1. Cliente → POST /api/login (username + password)
   ↓
2. API Gateway → Roteia para Login Service
   ↓
3. Login Service → Valida credenciais (BCrypt)
   ↓
4. Login Service → Gera JWT Token (HMAC SHA-256)
   ↓
5. API Gateway → Retorna token ao cliente
   ↓
6. Cliente → Armazena token
   ↓
7. Cliente → GET /api/user/profile (Header: Authorization: Bearer <token>)
   ↓
8. API Gateway → Roteia com header
   ↓
9. Login Service → JwtAuthenticationFilter valida token
   ↓
10. Login Service → Retorna dados (se token válido)
```

---

## 📁 Estrutura do Projeto

```
modulo2/
├── 📄 docker-compose.yml           # Orquestração dos containers
├── 📄 README.md                    # Este arquivo
│
├── 📂 api-gateway/                 # Serviço de Gateway
│   ├── 🐳 Dockerfile
│   ├── 📄 pom.xml
│   └── 📂 src/main/
│       ├── 📂 java/br/com/fatec/modulo2/apigateway/
│       │   └── 📄 ApiGatewayApplication.java
│       └── 📂 resources/
│           └── 📄 application.yml
│
└── 📂 login-service/               # Serviço de Autenticação
    ├── 🐳 Dockerfile
    ├── 📄 pom.xml
    └── 📂 src/main/
        ├── 📂 java/br/com/fatec/modulo2/loginservice/
        │   ├── 📄 LoginServiceApplication.java
        │   ├── 📂 config/
        │   │   └── 📄 SecurityConfig.java
        │   ├── 📂 controller/
        │   │   ├── 📄 LoginController.java
        │   │   ├── 📄 RegisterController.java
        │   │   └── 📄 UserController.java
        │   ├── 📂 dto/
        │   │   ├── 📄 LoginRequest.java
        │   │   ├── 📄 LoginResponse.java
        │   │   └── 📄 RegisterRequest.java
        │   ├── 📂 filter/
        │   │   └── 📄 JwtAuthenticationFilter.java
        │   └── 📂 service/
        │       ├── 📄 AuthService.java
        │       └── 📄 JwtService.java
        └── 📂 resources/
            └── 📄 application.yml
```

---

## 🚀 Como Executar

### Pré-requisitos

- ✅ **Docker** instalado (versão 20.10+)
- ✅ **Docker Compose** instalado (versão 2.0+)
- ✅ **Java 21** (opcional, apenas para desenvolvimento local)
- ✅ **Maven 3.9+** (opcional, apenas para desenvolvimento local)

### Executando com Docker Compose (Recomendado)

```bash
# 1. Clone o repositório
git clone <seu-repositorio>
cd modulo2

# 2. Build e start dos containers
docker-compose up --build

# 3. Aguarde as mensagens de inicialização:
# ✓ api-gateway started on port 8080
# ✓ login-service started on port 8081
```

### Verificando se está funcionando

```bash
# Health check do Gateway
curl http://localhost:8080/health

# Documentação das rotas
curl http://localhost:8080/

# Health check do Login Service (através do Gateway)
curl http://localhost:8080/api/health
```

### Parando os containers

```bash
# Parar containers
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

---

## 📡 Endpoints Disponíveis

### 🌍 API Gateway (Porta 8080)

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/` | GET | Documentação completa da API |
| `/health` | GET | Health check do Gateway |

### 🔓 Rotas Públicas (Não requerem autenticação)

| Endpoint | Método | Descrição | Body |
|----------|--------|-----------|------|
| `/api/login` | POST | Autenticação de usuário | `{"username":"string","password":"string"}` |
| `/api/register` | POST | Registro de novo usuário | `{"username":"string","password":"string"}` |
| `/api/health` | GET | Status do Login Service | - |

### 🔒 Rotas Protegidas (Requerem Bearer Token)

| Endpoint | Método | Descrição | Header Obrigatório |
|----------|--------|-----------|-------------------|
| `/api/user/profile` | GET | Perfil do usuário autenticado | `Authorization: Bearer <token>` |
| `/api/user/secret` | GET | Dados secretos protegidos | `Authorization: Bearer <token>` |
| `/api/user/update` | PUT | Atualizar dados do usuário | `Authorization: Bearer <token>` |

---

## 🧪 Testes

### 1️⃣ Testar Login (Rota Pública)

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Resposta Esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwidHlwZSI6IkJlYXJlciIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzMzMDY4ODAwLCJleHAiOjE3MzMxNTUyMDB9.xyz...",
  "type": "Bearer",
  "expiresIn": 86400000,
  "issuedAt": "2024-12-01T10:30:00"
}
```

### 2️⃣ Testar Registro (Rota Pública)

```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "novousuario",
    "password": "senha123456"
  }'
```

**Resposta Esperada (201 Created):**
```json
{
  "message": "Usuário registrado com sucesso",
  "username": "novousuario"
}
```

### 3️⃣ Testar Rota Protegida COM Token

```bash
# Salvar o token em uma variável
TOKEN=$(curl -s -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq -r '.token')

# Acessar rota protegida
curl http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta Esperada (200 OK):**
```json
{
  "username": "admin",
  "message": "Perfil do usuário autenticado",
  "authenticated": true
}
```

### 4️⃣ Testar Rota Protegida SEM Token (Deve Falhar)

```bash
curl http://localhost:8080/api/user/profile
```

**Resposta Esperada (403 Forbidden):**
```json
{
  "timestamp": "2024-12-01T15:30:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "path": "/user/profile"
}
```

### 5️⃣ Verificar Isolamento do Container

```bash
# Tentar acessar Login Service diretamente (DEVE FALHAR)
curl http://localhost:8081/login
# curl: (7) Failed to connect to localhost port 8081: Connection refused

# Acessar através do Gateway (DEVE FUNCIONAR)
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
# ✓ Retorna token
```

### 🔐 Usuários Pré-cadastrados

| Username | Password | Descrição |
|----------|----------|-----------|
| `admin` | `admin123` | Usuário administrador |
| `user` | `user123` | Usuário padrão |
| `test` | `test123` | Usuário de teste |

---

## 🔐 Segurança

### Implementações de Segurança

#### 1. **Criptografia de Senhas**
- ✅ Algoritmo: **BCrypt** (Blowfish Cipher)
- ✅ Senhas nunca armazenadas em texto plano
- ✅ Salt automático por senha

#### 2. **JWT (JSON Web Token)**
- ✅ Algoritmo: **HMAC SHA-256** (HS256)
- ✅ Tokens assinados digitalmente
- ✅ Expiração configurável (padrão: 24 horas)
- ✅ Claims customizados (username, type, etc)

#### 3. **Spring Security**
- ✅ Filtro customizado `JwtAuthenticationFilter`
- ✅ Rotas públicas: `/login`, `/register`, `/health`
- ✅ Rotas protegidas: `/user/**`
- ✅ Stateless session (não usa cookies de sessão)
- ✅ CSRF desabilitado (apropriado para API REST)

#### 4. **Isolamento de Rede**
- ✅ Login Service em rede Docker privada
- ✅ Apenas API Gateway expõe porta ao host
- ✅ Comunicação interna via nome do container

#### 5. **CORS Configurado**
- ✅ Headers permitidos: `*`
- ✅ Métodos: GET, POST, PUT, DELETE, OPTIONS
- ✅ Header `Authorization` exposto

### Configurações de Segurança

```yaml
# application.yml (Login Service)
jwt:
  secret: "chave-secreta-super-segura-256-bits-minimo"
  expiration: 86400000  # 24 horas
```

⚠️ **IMPORTANTE**: Em produção:
- Use variáveis de ambiente para secrets
- Implemente refresh tokens
- Configure HTTPS/TLS
- Adicione rate limiting
- Implemente logs de auditoria