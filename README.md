# DESAFIO DSMovie Jacoco

## Resumo do Projeto

O DSMovie é um sistema de gestão de filmes e avaliações de filmes. Ele permite que os usuários visualizem informações sobre filmes e registrem suas avaliações. As principais funcionalidades e regras de negócio do projeto incluem:

- **Filmes e Avaliações:**
  - A visualização dos dados dos filmes é pública, não requerendo login.
  - A inserção, atualização e exclusão de filmes são restritas a usuários com perfil de ADMIN.
  - As avaliações de filmes podem ser registradas por qualquer usuário logado, seja CLIENT ou ADMIN.
  - Cada filme pode receber uma nota de 0 a 5 (score) de cada usuário.
  - O sistema calcula a média das notas de todos os usuários para cada filme e armazena essa média (score) na entidade Movie, juntamente com a contagem de votos (count).

## Tecnologias Utilizadas

O projeto DSMovie foi desenvolvido utilizando as seguintes tecnologias:

- Java
- Spring Boot
- Hibernate
- Banco de dados (H2)
- JUnit e Mockito para testes unitários
- Jacoco para medição de cobertura de código

## Configuração do Ambiente

Antes de executar o projeto, certifique-se de ter as seguintes dependências instaladas:

- Java JDK
- Maven (para compilação e gerenciamento de dependências)
- Banco de dados configurado e acessível

## Executando o Projeto

1. Clone o repositório DSMovie para o seu ambiente local.
2. Configure o banco de dados de acordo com as propriedades definidas no arquivo `application.properties`.
3. Execute o comando `mvn spring-boot:run` na raiz do projeto para iniciar a aplicação.
4. Acesse a aplicação em `http://localhost:8080` no navegador web.

## Testes Unitários

O objetivo deste desafio é implementar testes unitários para os serviços principais do projeto DSMovie. Os serviços a serem testados são:

### MovieServiceTests:

- `findAllShouldReturnPagedMovieDTO`
- `findByIdShouldReturnMovieDTOWhenIdExists`
- `findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `insertShouldReturnMovieDTO`
- `updateShouldReturnMovieDTOWhenIdExists`
- `updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldDoNothingWhenIdExists`
- `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldThrowDatabaseExceptionWhenDependentId`

### ScoreServiceTests:

- `saveScoreShouldReturnMovieDTO`
- `saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId`

### UserServiceTests:

- `authenticatedShouldReturnUserEntityWhenUserExists`
- `authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist`
- `loadUserByUsernameShouldReturnUserDetailsWhenUserExists`
- `loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist`


O projeto DSMovie inclui testes unitários para garantir a qualidade e robustez do código. Para executar os testes após o desafio ter sido implementado, siga os passos abaixo:

1. Certifique-se de ter as dependências de teste configuradas corretamente no arquivo `pom.xml`.
2. Execute o comando `mvn test` na raiz do projeto para executar os testes.
3. Verifique a saída do console para garantir que todos os testes tenham sido executados com êxito.

## objetivo

É necessário garantir que os testes cubram todas as funcionalidades dos serviços mencionados acima e que a cobertura de testes, conforme medida pelo Jacoco, seja de 100%.

## Cobertura de Testes

O Jacoco é usado para medir a cobertura de código dos testes. Após a execução dos testes, você pode visualizar o relatório de cobertura gerado na pasta `target/site/jacoco/index.html`.


