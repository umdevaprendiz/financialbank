# 💰 FinancialBank

API RESTful desenvolvida em **Java** com **Spring Boot** para criação e administração de contas bancárias, controle de transações e transferências entre contas.

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/Railway-0B0D0E?style=for-the-badge&logo=railway&logoColor=white" alt="Railway"/>
</div>

---

## 📌 Sobre o Projeto

O **FinancialBank** é uma API RESTful que simula as operações essenciais de uma instituição financeira: criação e administração de contas, controle de acesso por perfil de usuário, registro e filtragem de transações, e transferências seguras entre contas. O projeto foi construído como parte do meu portfólio, com foco em boas práticas de arquitetura backend.

---

## ✅ Funcionalidades Atuais

### Arquitetura
- Estrutura em camadas completa: **Entities**, **Repositories**, **Services**, **DTOs** e **Controllers**
- Separação clara de responsabilidades seguindo os princípios do Spring Boot

### Segurança
- Autenticação e autorização com **Spring Security**
- Controle de acesso baseado em papéis (**RBAC**): `ADMIN`, `MANAGER` e `USER`

### Transações
- Filtragem dinâmica de transações utilizando **Specification** (JPA Criteria API)
- Funcionalidade de **transferência entre contas** com controle transacional via `@Transactional`, garantindo atomicidade e consistência dos dados

### Deploy
- Containerização com **Docker**
- Deploy realizado na **Railway**
- Documentação do processo de deploy

### Qualidade
- Diversas correções de bugs realizadas ao longo do desenvolvimento, aumentando a estabilidade da aplicação

---

## 🚧 Roadmap — Próximas Melhorias

Este projeto irá passar por uma nova rodada de melhorias para elevar o nível de profissionalismo e aderência a práticas de mercado:

- [ ] **Testes automatizados** com JUnit 5 + Mockito, cobrindo services e regras de negócio críticas (transferências, saldo)
- [ ] **Documentação da API com Swagger/OpenAPI**, facilitando a exploração e testes dos endpoints
- [ ] **Tratamento centralizado de exceções** com `@RestControllerAdvice`, padronizando as respostas de erro da API
- [ ] **Validação de entrada** com `@Valid` e Bean Validation nos DTOs, especialmente em campos monetários e de identificação
- [ ] **Paginação** nos endpoints de listagem de transações
- [ ] **README aprimorado** com diagrama de arquitetura e exemplos de request/response

---

## 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologias |
|---|---|
| Linguagem | Java |
| Framework | Spring Boot |
| Segurança | Spring Security |
| Persistência | Spring Data JPA |
| Containerização | Docker |
| Deploy | Railway |
| Testes de API | Postman |

---

## 🚀 Como Executar o Projeto

```bash
# Clone o repositório
git clone https://github.com/umdevaprendiz/financialbankk.git

# Acesse a pasta do projeto
cd financialbankk

# Suba a aplicação com Docker
docker-compose up -d

# Ou execute localmente com Maven
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080` (porta padrão — ajuste conforme sua configuração local).

---

## 👤 Autor

**Sérgio Guilherme**
Estudante de Ciência da Computação (UFPB) | Desenvolvedor Backend Java/Spring Boot

- GitHub: [github.com/umdevaprendiz](https://github.com/umdevaprendiz)
- E-mail: sergiodeveloperprofissional27@gmail.com

---

<div align="center">
  <sub>Projeto em constante evolução como parte do meu portfólio de desenvolvimento backend.</sub>
</div>
