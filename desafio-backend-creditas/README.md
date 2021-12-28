# Desafio Back-end Creditas

Esta é uma possível solução para o desafio que no presente momento (27/12/2021) está disponível publicamente no 
[GitHub da Creditas](https://github.com/Creditas/challenge/tree/master/backend/code-challenges).

Essa solução tem como único e exclusivo objetivo, ser de cunho educacional. Assim sendo, tomei a liberdade de 
incluir na solução, alguns itens que não são solicitados no desafio:

- A solução foi implementada utilizando o framework [Spring Boot](https://spring.io/projects/spring-boot);
- Criamos alguns testes automatizados para testarmos a solução.
- Acrescentamos uma documentação para a API desenvolvida.

## Construindo e Executando a Aplicação
### Pré-requisitos
- [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Maven 3](https://maven.apache.org/download.cgi)

### Executando a aplicação 
- Faça o download do código fonte do projeto;
- Utilizando o terminal ou prompt de comandos, acesse o diretório/pasta onde está o código fonte que foi baixado;
- Ainda no terminal/prompt de comandos, execute o seguinte comando:
```sh
mvn clean spring-boot:run
```

### Acessando o Endpoint
Se tudo ocorreu bem, o endpoint estará disponível através do método [HTTP Post](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods) no seguinte endreço: [http://localhost:8080/api/analyze](http://localhost:8080/api/analyze)

### Acessando a Documentação
A documentação deverá estar disponível no seguinte endereço: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) 
