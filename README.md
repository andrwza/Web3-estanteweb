# 📚 EstanteWeb

> Livros usados com história.

O EstanteWeb é um sistema web de uma livraria de livros usados. Pelo navegador dá para ver o acervo e cadastrar, corrigir e remover livros. Os leitores também podem sugerir títulos que gostariam de encontrar na estante.

Fiz o projeto para estudar desenvolvimento web com **Java e Spring Boot**: rotas, formulários, templates, validação e acesso a banco de dados.

## ✨ O que o sistema faz

### Acervo de livros
- **Listar** todos os livros, com opção de ordenar por título, autor, ano ou preço
- **Ver os detalhes** de cada livro (título, autor, ano e preço)
- **Cadastrar** um novo livro pelo formulário
- **Corrigir** os dados de um livro já cadastrado
- **Remover** um livro, com confirmação antes de apagar
- **Buscar** livros até um preço máximo
- **Contar** quantos livros há no acervo

### Sugestões de leitura
- Os leitores mandam sugestões pelo formulário (nome + livro sugerido)
- Todas as sugestões ficam salvas e aparecem numa página própria

### Validações
- Título e autor não podem ficar em branco
- O preço precisa ser maior que zero
- Quando algo está errado, o formulário volta com uma mensagem de erro e com o que já tinha sido digitado

## 🛠️ Tecnologias

| Tecnologia | Para quê |
|---|---|
| **Java 21** | Linguagem do projeto |
| **Spring Boot 4** (Web MVC) | Rotas, controllers e servidor web |
| **FreeMarker** | Templates HTML (`.ftlh`) |
| **Spring JDBC** (`JdbcTemplate`) | Consultas SQL ao banco |
| **MySQL** | Banco de dados |
| **Flyway** | Criação das tabelas e dados iniciais (migrations) |
| **Maven** | Build e dependências |

## 🗂️ Estrutura

```
src/main/
├── java/br/com/estanteweb/
│   ├── DemoApplication.java       # Ponto de entrada da aplicação
│   ├── LivroController.java       # Rotas do acervo (listar, cadastrar, editar, remover)
│   ├── LivroRepository.java       # SQL da tabela livro
│   ├── Livro.java                 # Modelo de livro
│   ├── SugestaoController.java    # Rotas de sugestões
│   ├── SugestaoRepository.java    # SQL da tabela sugestao
│   ├── Sugestao.java              # Modelo de sugestão
│   └── PaginaController.java      # Início, sobre e contato
└── resources/
    ├── templates/                 # Páginas HTML (FreeMarker)
    ├── db/migration/              # Scripts do Flyway (V1, V2, V3...)
    └── application.properties     # Configuração do banco
```

## 🧭 Principais rotas

| Método | Rota | O que faz |
|---|---|---|
| GET | `/livros/html` | Lista o acervo (`?ordem=titulo`, `autor`, `ano` ou `preco`) |
| GET | `/livros/{id}` | Detalhes de um livro |
| GET | `/livros/novo` | Formulário de cadastro |
| POST | `/livros` | Salva um novo livro |
| GET | `/livros/{id}/editar` | Formulário de edição |
| POST | `/livros/{id}/editar` | Salva a correção |
| POST | `/livros/{id}/remover` | Remove o livro |
| GET | `/livros/busca?precoMaximo=25` | Livros até um preço |
| GET | `/livros/total` | Total de livros no acervo |
| GET | `/sugestoes/nova` | Formulário de sugestão |
| POST | `/sugestoes` | Envia uma sugestão |
| GET | `/sugestoes` | Lista as sugestões recebidas |
| GET | `/contato` | Formulário de contato |

> 💡 Ações que **alteram dados** (cadastrar, editar, remover) usam **POST** e ações que só **leem** usam **GET**. Assim, ninguém apaga um livro só por abrir um link.

## 🚀 Como rodar

**Pré-requisitos:** Java 21 e MySQL rodando na porta `3306`.

1. Crie o banco:
   ```sql
   CREATE DATABASE estanteweb;
   ```
2. Ajuste o usuário e a senha do MySQL em `src/main/resources/application.properties`, se necessário.
3. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run        # Linux/macOS
   mvnw.cmd spring-boot:run      # Windows
   ```
4. Acesse **http://localhost:8080/livros/html** 🎉

Na primeira execução, o Flyway cria as tabelas `livro` e `sugestao` e já cadastra alguns livros de exemplo.

---

Feito por **Andreza Damasceno** 💜
