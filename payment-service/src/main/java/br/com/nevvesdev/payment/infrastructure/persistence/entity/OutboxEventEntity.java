package br.com.nevvesdev.payment.infrastructure.persistence.entity;

import br.com.nevvesdev.payment.domain.model.OutboxEvent;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
public class OutboxEventEntity {

    @Id
    private UUID id;

    @Column(name = "tipo_agregado", nullable = false)
    private String tipoAgregado;

    @Column(name = "id_agregado", nullable = false)
    private UUID idAgregado;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private boolean processado;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    public OutboxEventEntity() {}

    public static OutboxEventEntity de(OutboxEvent evento) {
        var entity = new OutboxEventEntity();
        entity.id = evento.getId();
        entity.tipoAgregado = evento.getTipoAgregado();
        entity.idAgregado = evento.getIdAgregado();
        entity.tipoEvento = evento.getTipoEvento();
        entity.payload = evento.getPayload();
        entity.processado = evento.isProcessado();
        entity.criadoEm = evento.getCriadoEm();
        return entity;
    }

    public OutboxEvent toDomain() {
        return new OutboxEvent(id, tipoAgregado, idAgregado, tipoEvento, payload, processado, criadoEm);
    }

    public UUID getId() { return id; }
    public String getTipoAgregado() { return tipoAgregado; }
    public UUID getIdAgregado() { return idAgregado; }
    public String getTipoEvento() { return tipoEvento; }
    public String getPayload() { return payload; }
    public boolean isProcessado() { return processado; }
    public Instant getCriadoEm() { return criadoEm; }
}