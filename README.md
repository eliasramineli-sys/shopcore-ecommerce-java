# ShopCore API — E-commerce Java

Backend REST educacional de e-commerce em **Java 21, Spring Boot 3.5.7, PostgreSQL, JPA, Flyway e Maven**. Foco em regras de negócio, persistência e testes para portfólio.

## Funcionalidades
- Catálogo de produtos com SKU único, preço em `BigDecimal` e estoque.
- Carrinho de compras por e-mail, com múltiplos itens.
- Checkout transacional: valida estoque, desconta produtos, grava pedido e fecha carrinho.
- Histórico de pedido com **snapshot** do nome e preço dos produtos.
- Prevenção de checkout duplicado via bloqueio pessimista do carrinho e controle otimista (`@Version`) dos produtos.
- Paginação de catálogo, validação HTTP e tratamento de erros.
- Migração de banco com Flyway, Docker Compose e CI com GitHub Actions.

## Rodar no Eclipse
Requisitos: **JDK 21+**, Maven e Docker Compose.

```bash
docker compose up -d
mvn clean test
mvn spring-boot:run
```

Importe no Eclipse via **File > Import > Maven > Existing Maven Projects**. API: `http://localhost:8080`; PostgreSQL: porta local `5435`. Credenciais do compose são apenas para desenvolvimento; altere-as em qualquer ambiente real. Variáveis opcionais: `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Endpoints
| Método | Rota | Ação |
| --- | --- | --- |
| POST | `/api/v1/products` | Cadastrar produto |
| GET | `/api/v1/products` | Listar produtos paginados |
| POST | `/api/v1/carts` | Criar carrinho |
| GET | `/api/v1/carts/{id}` | Consultar carrinho |
| POST | `/api/v1/carts/{id}/items` | Adicionar item |
| POST | `/api/v1/carts/{id}/checkout` | Finalizar pedido |
| GET | `/api/v1/orders/{id}` | Consultar pedido |

### Exemplos de JSON
**POST /api/v1/products**
```json
{"sku":"MOUSE-001","name":"Mouse sem fio","price":79.90,"stock":20}
```
**POST /api/v1/carts**
```json
{"customerEmail":"cliente@example.com"}
```
**POST /api/v1/carts/1/items**
```json
{"productId":1,"quantity":2}
```
Depois execute `POST /api/v1/carts/1/checkout` com corpo vazio.

## Arquitetura
- `domain`: entidades e invariantes do estoque, carrinho e pedido.
- `repository`: interfaces Spring Data JPA.
- `service`: orquestração e transações.
- `api`: controllers, DTOs e erros.
- `db/migration`: schema versionado.
- `src/test`: testes unitários do domínio.

## Limitações e próximos passos
**Não há autenticação/autorização**; qualquer cliente que conheça os IDs pode acessar os recursos. Não exponha publicamente sem implementar segurança e vincular carrinhos/pedidos ao usuário autenticado. Não há pagamento real: checkout cria pedido com status `PLACED` e simula a confirmação. Não existem frete, impostos, cupons, cancelamento, devoluções, reabastecimento, idempotency key ou integração de pagamento. O fluxo de concorrência usa `@Version` e bloqueio do carrinho; falhas de atualização concorrente exigem retry do cliente. Os testes CI atuais são **unitários** e não substituem testes de integração com PostgreSQL/Testcontainers.

### Atenção à consistência
A transação de checkout reverte mudanças se falhar, mas `@Version` é usado para detectar concorrência entre atualizações de produto; o código não implementa bloqueio pessimista individual dos produtos. Para uma aplicação real, seriam necessários testes concorrentes e política explícita de retry.

Projeto educacional para portfólio; não pronto para produção.
