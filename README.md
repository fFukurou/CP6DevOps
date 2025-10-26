
# API de Mitologia Grega

Eu estava jogando Hades II...

## Endpoints (Está tudo no Swagger também)
(http://localhost:8080/swagger-ui/index.html)

---

#### Link Do Projeto Funcinando:
- https://www.youtube.com/watch?v=dQw4w9WgXcQ

---

### Tipos de Divindade (`/api/deity-types`)

Gerencia as categorias de seres mitológicos (Deus, Titã, Semideus, etc.).

* **`GET /api/deity-types`**
    * **Descrição:** Lista todos os tipos de divindades, com suporte a paginação e filtros.
    * **Parâmetros de Query (Opcionais):**
        * `typeName` (string): Filtra por nome do tipo (busca parcial, case-insensitive). Ex: `?typeName=God`
        * `origin` (string): Filtra pela origem (busca parcial, case-insensitive). Ex: `?origin=Olympus`
        * `page` (int): Número da página (começa em 0).
        * `size` (int): Tamanho da página.
        * `sort` (string): Campo para ordenação e direção (ex: `typeName,asc`).
    * **Resposta:** Página (`Page`) de objetos `ReadDeityTypeDto`.

* **`POST /api/deity-types`**
    * **Descrição:** Cria um novo tipo de divindade.
    * **Corpo da Requisição:** Objeto `CreateDeityTypeDto`.
        ```json
        {
          "typeName": "Nome do Tipo",
          "origin": "Origem...",
          "description": "Descrição...",
          "lifespan": "Tempo de vida...",
          "powerSource": "Fonte de poder..."
        }
        ```
    * **Resposta (201 Created):** Objeto `ReadDeityTypeDto` do tipo criado.
    * **Respostas de Erro:** `400 Bad Request` (dados inválidos), `409 Conflict` (nome já existe).

* **`GET /api/deity-types/{id}`**
    * **Descrição:** Busca um tipo de divindade pelo seu ID.
    * **Parâmetro de Path:** `id` (long) - O ID do tipo de divindade.
    * **Resposta (200 OK):** Objeto `ReadDeityTypeDto`.
    * **Resposta de Erro:** `404 Not Found`.

* **`PUT /api/deity-types/{id}`**
    * **Descrição:** Atualiza um tipo de divindade existente pelo ID. Permite atualizações parciais.
    * **Parâmetro de Path:** `id` (long) - O ID do tipo de divindade a ser atualizado.
    * **Corpo da Requisição:** Objeto `UpdateDeityTypeDto` (campos são opcionais).
        ```json
        {
          "typeName": "Novo Nome",
          "description": "Descrição atualizada..."
        }
        ```
    * **Resposta (200 OK):** Objeto `ReadDeityTypeDto` do tipo atualizado.
    * **Respostas de Erro:** `400 Bad Request`, `404 Not Found`, `409 Conflict` (se o novo nome já existir).

* **`DELETE /api/deity-types/{id}`**
    * **Descrição:** Deleta um tipo de divindade pelo ID. **Atenção:** Isso também deletará todas as Divindades associadas a este tipo (devido ao `CascadeType.REMOVE`, é a vida).
    * **Parâmetro de Path:** `id` (long) - O ID do tipo de divindade a ser deletado.
    * **Resposta:** `204 No Content`.
    * **Resposta de Erro:** `404 Not Found`.

---

### Divindades (`/api/deities`)

Gerencia as divindades individuais (Zeus, Artemis, Heracles, etc.).

* **`GET /api/deities`**
    * **Descrição:** Lista todas as divindades, com suporte a paginação e filtros.
    * **Parâmetros de Query (Opcionais):**
        * `name` (string): Filtra por nome da divindade (busca parcial, case-insensitive). Ex: `?name=Zeus`
        * `domain` (string): Filtra pelo domínio (busca parcial, case-insensitive). Ex: `?domain=Sky`
        * `deityTypeId` (long): Filtra pelo ID do tipo de divindade. Ex: `?deityTypeId=1`
        * `page` (int): Número da página.
        * `size` (int): Tamanho da página.
        * `sort` (string): Campo para ordenação e direção (ex: `name,desc`).
    * **Resposta:** Página (`Page`) de objetos `ReadDeityDto`.

* **`POST /api/deities`**
    * **Descrição:** Cria uma nova divindade.
    * **Corpo da Requisição:** Objeto `CreateDeityDto`.
        ```json
        {
          "name": "Nome da Divindade",
          "domain": "Domínio(s)...",
          "romanName": "Nome Romano (opcional)",
          "description": "Descrição...",
          "deityTypeId": 1
        }
        ```
    * **Resposta (201 Created):** Objeto `ReadDeityDto` da divindade criada.
    * **Respostas de Erro:** `400 Bad Request` (dados inválidos ou `deityTypeId` não encontrado), `409 Conflict` (nome já existe).

* **`GET /api/deities/{id}`**
    * **Descrição:** Busca uma divindade pelo seu ID.
    * **Parâmetro de Path:** `id` (long) - O ID da divindade.
    * **Resposta (200 OK):** Objeto `ReadDeityDto`.
    * **Resposta de Erro:** `404 Not Found`.

* **`PUT /api/deities/{id}`**
    * **Descrição:** Atualiza uma divindade existente pelo ID. Permite atualizações parciais e mudança do tipo (`deityTypeId`).
    * **Parâmetro de Path:** `id` (long) - O ID da divindade a ser atualizada.
    * **Corpo da Requisição:** Objeto `UpdateDeityDto` (campos são opcionais).
        ```json
        {
          "name": "Novo Nome",
          "domain": "Domínio atualizado...",
          "deityTypeId": 2
        }
        ```
    * **Resposta (200 OK):** Objeto `ReadDeityDto` da divindade atualizada.
    * **Respostas de Erro:** `400 Bad Request` (dados inválidos ou `deityTypeId` não encontrado), `404 Not Found`, `409 Conflict` (se o novo nome já existir).

* **`DELETE /api/deities/{id}`**
    * **Descrição:** Deleta uma divindade pelo ID.
    * **Parâmetro de Path:** `id` (long) - O ID da divindade a ser deletada.
    * **Resposta:** `204 No Content`.
    * **Resposta de Erro:** `404 Not Found`.

---

## Integrantes

2TDSPM:

- Vicenzo Massao - 554833
- Erick Alves - 556862
