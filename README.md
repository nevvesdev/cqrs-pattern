# cqrs-outbox-cdc

Implementação dos padrões **CQRS**, **Transactional Outbox** e **CDC** com dois microsserviços em Java 21 + Spring Boot 4.0.7.

---

## Visão geral

Este projeto demonstra como garantir consistência eventual entre microsserviços sem Two-Phase Commit (2PC), usando o padrão Transactional Outbox como contrato de entrega confiável de eventos.

### O problema resolvido

Em arquiteturas de microsserviços, gravar no banco de dados e publicar um evento no Kafka em operações separadas cria risco de inconsistência: o banco pode ser atualizado enquanto o Kafka falha, ou vice-versa. O Transactional Outbox resolve isso garantindo atomicidade entre as duas operações.

---

---

## Padrões implementados

### CQRS — Command Query Responsibility Segregation
- **Command Side** (`payment-service`): recebe e processa comandos de criação de pagamento, grava no banco de escrita
- **Query Side** (`ledger-service`): mantém uma visão otimizada para leitura, atualizada via eventos

### Transactional Outbox
- `Payment` e `OutboxEvent` são gravados na **mesma transação** do banco de dados
- Elimina o risco de publicar um evento sem ter gravado o dado, ou gravar o dado sem publicar o evento

### CDC — Change Data Capture (via polling)
- O `OutboxPoller` roda a cada 5 segundos, lê os eventos não processados da tabela `outbox_events`, publica no Kafka e os marca como processados
- Em produção, essa responsabilidade seria do Debezium, mas o polling manual demonstra o mesmo contrato de forma mais transparente

### Idempotência
- O `ledger-service` verifica se um `pagamentoId` já foi processado antes de gravar o lançamento
- A coluna `pagamento_id` na tabela `lancamentos_ledger` tem constraint `UNIQUE` como segunda camada de proteção

---

## Stack

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.7 | Framework principal |
| Spring Data JPA | — | Persistência |
| Spring Kafka | — | Produtor e consumidor Kafka |
| PostgreSQL | 16 | Banco de dados (dois schemas) |
| Flyway | — | Migrations |
| Apache Kafka | 7.6.0 (Confluent) | Broker de mensagens |
| Springdoc OpenAPI | 3.1.0 | Documentação da API |
| Maven | — | Build e módulos |
| Docker Compose | — | Infraestrutura local |

---

## Exemplos de uso

### Criar um pagamento

```bash
curl -X POST http://localhost:8080/pagamentos \
  -H "Content-Type: application/json" \
  -d '{
    "contaOrigem": "CC-001",
    "contaDestino": "CC-002",
    "valor": 250.00
  }'
```

Resposta: `201 Created` com header `Location: /pagamentos/{id}`

### Consultar um pagamento

```bash
curl http://localhost:8080/pagamentos/{id}
```

### Consultar extrato de uma conta

```bash
curl http://localhost:8081/ledger/CC-001
```

Resposta:
```json
{
  "numeroConta": "CC-001",
  "totalLancamentos": 1,
  "saldoTotal": -250.00,
  "lancamentos": [...]
}
```

---

## Fases do projeto

| Fase | Descrição |
|------|-----------|
| 0 | Scaffold: repositório, módulos Maven, Docker Compose |
| 1 | Domínio do `payment-service`: `Payment`, `OutboxEvent`, ports |
| 2 | Command side: handler, JPA, migrations Flyway |
| 3 | CDC Poller com `@Scheduled` publicando no Kafka |
| 4 | `ledger-service`: consumer Kafka, persistência, idempotência |
| 5 | Query side REST: endpoints de pagamento e extrato |
| 6 | Tratamento global de erros e resposta padronizada |
| 7 | README profissional |