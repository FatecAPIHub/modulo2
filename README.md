# modulo2
API REST de Login com controle de acesso por usuário e senha


## ROUTES
 
| Rota | Método | Autenticação | Descrição |
|------|--------|--------------|-----------|
| `/login` | POST | ❌ Pública | Login de usuários |
| `/register` | POST | ❌ Pública | Registro de novos usuários |
| `/health` | GET | ❌ Pública | Health check |
| `/user/profile` | GET | ✅ Requer Token | Ver perfil |
| `/user/secret` | GET | ✅ Requer Token | Dados secretos |
| `/user/update` | PUT | ✅ Requer Token | Atualizar dados |
