package br.com.nevvesdev.ledger.infrastructure.messaging;

import br.com.nevvesdev.ledger.domain.model.LancamentoLedger;
import br.com.nevvesdev.ledger.infrastructure.persistence.adapter.LancamentoLedgerRepositoryAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class ConsumidorPagamentoKafka {

    private static final Logger log = LoggerFactory.getLogger(ConsumidorPagamentoKafka.class);

    private final LancamentoLedgerRepositoryAdapter repositoryAdapter;
    private final ObjectMapper objectMapper;

    public ConsumidorPagamentoKafka(
            LancamentoLedgerRepositoryAdapter repositoryAdapter,
            ObjectMapper objectMapper
    ) {
        this.repositoryAdapter = repositoryAdapter;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "pagamento.eventos", groupId = "ledger-group")
    public void consumir(String mensagem) {
        try {
            JsonNode json = objectMapper.readTree(mensagem);

            UUID pagamentoId = UUID.fromString(json.get("paymentId").asText());

            if (repositoryAdapter.jaProcessado(pagamentoId)) {
                log.warn("[ConsumidorKafka] Evento duplicado ignorado. PagamentoId={}", pagamentoId);
                return;
            }

            var lancamento = LancamentoLedger.criar(
                    pagamentoId,
                    json.get("contaOrigem").asText(),
                    json.get("contaDestino").asText(),
                    new BigDecimal(json.get("valor").asText()),
                    json.get("status").asText()
            );

            repositoryAdapter.salvar(lancamento);
            log.info("[ConsumidorKafka] Lançamento registrado no ledger. PagamentoId={}", pagamentoId);

        } catch (Exception ex) {
            log.error("[ConsumidorKafka] Falha ao processar mensagem. Erro={}", ex.getMessage());
        }
    }
}