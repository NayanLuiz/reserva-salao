# Reserva de Salão de Condomínio

Sistema de gerenciamento de reservas de salões em condomínios, desenvolvido com Spring Boot e JavaFX.

## 📋 Funcionalidades

- **Gerenciamento de Condomínios**: Cadastro e controle de condomínios
- **Gerenciamento de Casas**: Registro de unidades habitacionais e seus responsáveis
- **Gerenciamento de Salões**: Cadastro de áreas comuns disponíveis para reserva
- **Sistema de Reservas**: Agendamento de salões com validação de dados
- **Interface Gráfica**: Aplicação desktop com JavaFX para fácil utilização
- **API REST**: Endpoints para integração com outros sistemas

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **PostgreSQL** (produção)
- **H2 Database** (testes)
- **JavaFX 17.0.6** (interface gráfica)
- **Lombok** (redução de código boilerplate)
- **Maven** (gerenciamento de dependências)

## 📦 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- PostgreSQL 12+ (para ambiente de produção)
- Docker e Docker Compose (opcional, para execução com containers)

## 🚀 Como Executar

### Usando Maven

1. Clone o repositório:
```bash
git clone https://github.com/NayanLuiz/reserva-salao.git
cd reserva-salao
```

2. Configure o banco de dados PostgreSQL no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reserva_salao
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute o projeto:
```bash
./mvnw spring-boot:run
```

### Usando Docker Compose

```bash
docker-compose up
```

A aplicação estará disponível em `http://localhost:8080`

## 📚 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/nayan/reserva_salao/
│   │   ├── controller/     # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # Entidades JPA
│   │   ├── exception/      # Tratamento de exceções
│   │   ├── repository/     # Repositórios de dados
│   │   ├── service/        # Lógica de negócio
│   │   └── view/           # Controladores JavaFX
│   └── resources/
│       └── application.properties
└── test/
    └── java/               # Testes unitários e de integração
```

## 🌐 Endpoints da API

### Condomínios
- `GET /api/condominio` - Listar todos os condomínios
- `GET /api/condominio/{id}` - Buscar condomínio por ID
- `POST /api/condominio` - Criar novo condomínio
- `DELETE /api/condominio/{id}` - Excluir condomínio

### Casas
- `GET /api/casa` - Listar todas as casas
- `GET /api/casa/{id}` - Buscar casa por ID
- `POST /api/casa` - Criar nova casa
- `PUT /api/casa/{id}` - Atualizar casa
- `DELETE /api/casa/{id}` - Excluir casa

### Salões
- `GET /api/salao` - Listar todos os salões
- `GET /api/salao/{id}` - Buscar salão por ID
- `POST /api/salao` - Criar novo salão
- `DELETE /api/salao/{id}` - Excluir salão

### Reservas
- `GET /api/reserva` - Listar todas as reservas
- `GET /api/reserva/{id}` - Buscar reserva por ID
- `POST /api/reserva` - Criar nova reserva
- `DELETE /api/reserva/{id}` - Excluir reserva

### Exemplo de Requisição

**POST /api/reserva**
```json
{
  "numero": 101,
  "condominio": "Residencial Planalto I",
  "salao": "Churrasqueira",
  "data": "2026-03-15"
}
```

## ✅ Validações

O sistema implementa validações robustas para garantir a integridade dos dados:

- **Número da casa**: Não pode ser nulo
- **Nome do condomínio**: Não pode estar vazio
- **Nome do salão**: Não pode estar vazio
- **Data da reserva**: Não pode ser nula e deve ser no presente ou futuro

Erros de validação retornam status HTTP 400 com detalhes dos campos inválidos.

## 🧪 Testes

Execute os testes com:
```bash
./mvnw test
```

O projeto inclui:
- Testes unitários de serviços
- Testes de integração de controladores
- Cobertura de casos de sucesso e falha

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

## 👥 Autor

Desenvolvido por [Nayan Luiz](https://github.com/NayanLuiz)