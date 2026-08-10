package br.com.nevvesdev.ledger.infrastructure.persistence.entity;

import br.com.nevvesdev.ledger.domain.model.LancamentoLedger;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "lancamentos_ledger")
public class LancamentoLedgerEntity {

    @Id
    private UUID id;

    @Column(name = "pagamento_id", nullable = false, unique = true)
    private UUID pagamentoId;

    @Column(name = "conta_origem", nullable = false)
    private String contaOrigem;

    @Column(name = "conta_destino", nullable = false)
    private String contaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String status;

    @Column(name = "processado_em", nullable = false)
    private Instant processadoEm;

    public LancamentoLedgerEntity() {}

    public static LancamentoLedgerEntity de(LancamentoLedger lancamento) {
        var entity = new LancamentoLedgerEntity();
        entity.id = lancamento.getId();
        entity.pagamentoId = lancamento.getPagamentoId();
        entity.contaOrigem = lancamento.getContaOrigem();
        entity.contaDestino = lancamento.getContaDestino();
        entity.valor = lancamento.getValor();
        entity.status = lancamento.getStatus();
        entity.processadoEm = lancamento.getProcessadoEm();
        return entity;
    }

    public LancamentoLedger toDomain() {
        return new LancamentoLedger(id, pagamentoId, contaOrigem, contaDestino, valor, status, processadoEm);
    }

    public UUID getId() { return id; }
    public UUID getPagamentoId() { return pagamentoId; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public BigDecimal getValor() { return valor; }
    public String getStatus() { return status; }
    public Instant getProcessadoEm() { return processadoEm; }
}