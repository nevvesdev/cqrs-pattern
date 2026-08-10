package br.com.nevvesdev.payment.domain.model;

import java.time.Instant;
import java.util.UUID;

public class OutboxEvent {

    private final UUID id;
    private final String tipoAgregado;
    private final UUID idAgregado;
    private final String tipoEvento;
    private final String payload;
    private boolean processado;
    private final Instant criadoEm;

    public OutboxEvent(
            UUID id,
            String tipoAgregado,
            UUID idAgregado,
            String tipoEvento,
            String payload,
            boolean processado,
            Instant criadoEm
    ) {
        this.id = id;
        this.tipoAgregado = tipoAgregado;
        this.idAgregado = idAgregado;
        this.tipoEvento = tipoEvento;
        this.payload = payload;
        this.processado = processado;
        this.criadoEm = criadoEm;
    }

    public static OutboxEvent de(Payment payment) {
        String payload = String.format(
                """
                {"paymentId":"%s","contaOrigem":"%s","contaDestino":"%s","valor":"%s","status":"%s"}
                """,
                payment.getId(),
                payment.getContaOrigem(),
                payment.getContaDestino(),
                payment.getValor().toPlainString(),
                payment.getStatus().name()
        ).strip();

        return new OutboxEvent(
                UUID.randomUUID(),
                "Payment",
                payment.getId(),
                "PAGAMENTO_CRIADO",
                payload,
                false,
                Instant.now()
        );
    }

    public void marcarComoProcessado() {
        this.processado = true;
    }

    public UUID getId() { return id; }
    public String getTipoAgregado() { return tipoAgregado; }
    public UUID getIdAgregado() { return idAgregado; }
    public String getTipoEvento() { return tipoEvento; }
    public String getPayload() { return payload; }
    public boolean isProcessado() { return processado; }
    public Instant getCriadoEm() { return criadoEm; }
}