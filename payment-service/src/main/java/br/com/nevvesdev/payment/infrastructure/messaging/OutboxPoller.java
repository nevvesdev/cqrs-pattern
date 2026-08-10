package br.com.nevvesdev.payment.infrastructure.messaging;

import br.com.nevvesdev.payment.domain.port.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxPoller {

    private static final Logger log = LoggerFactory.getLogger(OutboxPoller.class);

    private final OutboxEventRepository outboxEventRepository;
    private final PublicadorKafka publicadorKafka;

    public OutboxPoller(
            OutboxEventRepository outboxEventRepository,
            PublicadorKafka publicadorKafka
    ) {
        this.outboxEventRepository = outboxEventRepository;
        this.publicadorKafka = publicadorKafka;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processar() {
        var eventos = outboxEventRepository.buscarNaoProcessados();

        if (eventos.isEmpty()) {
            return;
        }

        log.info("[OutboxPoller] {} evento(s) pendente(s) encontrado(s).", eventos.size());

        for (var evento : eventos) {
            try {
                publicadorKafka.publicar(evento.getIdAgregado().toString(), evento.getPayload());
                evento.marcarComoProcessado();
                outboxEventRepository.atualizar(evento);
                log.info("[OutboxPoller] Evento processado. Id={} Tipo={}", evento.getId(), evento.getTipoEvento());
            } catch (Exception ex) {
                log.error("[OutboxPoller] Falha ao processar evento. Id={} Erro={}", evento.getId(), ex.getMessage());
            }
        }
    }
}