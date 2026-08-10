package br.com.nevvesdev.payment.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublicadorKafka {

    private static final Logger log = LoggerFactory.getLogger(PublicadorKafka.class);
    private static final String TOPICO = "pagamento.eventos";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PublicadorKafka(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(String chave, String payload) {
        kafkaTemplate.send(TOPICO, chave, payload)
                .whenComplete((resultado, ex) -> {
                    if (ex != null) {
                        log.error("[Kafka] Falha ao publicar evento. Chave={} Erro={}", chave, ex.getMessage());
                    } else {
                        log.info("[Kafka] Evento publicado. Chave={} Partição={} Offset={}",
                                chave,
                                resultado.getRecordMetadata().partition(),
                                resultado.getRecordMetadata().offset());
                    }
                });
    }
}