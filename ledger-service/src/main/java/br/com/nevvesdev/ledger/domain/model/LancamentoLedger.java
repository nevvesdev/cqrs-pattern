package br.com.nevvesdev.ledger.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class LancamentoLedger {

    private final UUID id;
    private final UUID pagamentoId;
    private final String contaOrigem;
    private final String contaDestino;
    private final BigDecimal valor;
    private final String status;
    private final Instant processadoEm;

    public LancamentoLedger(
            UUID id,
            UUID pagamentoId,
            String contaOrigem,
            String contaDestino,
            BigDecimal valor,
            String status,
            Instant processadoEm
    ) {
        this.id = id;
        this.pagamentoId = pagamentoId;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.status = status;
        this.processadoEm = processadoEm;
    }

    public static LancamentoLedger criar(
            UUID pagamentoId,
            String contaOrigem,
            String contaDestino,
            BigDecimal valor,
            String status
    ) {
        return new LancamentoLedger(
                UUID.randomUUID(),
                pagamentoId,
                contaOrigem,
                contaDestino,
                valor,
                status,
                Instant.now()
        );
    }

    public UUID getId() { return id; }
    public UUID getPagamentoId() { return pagamentoId; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public BigDecimal getValor() { return valor; }
    public String getStatus() { return status; }
    public Instant getProcessadoEm() { return processadoEm; }
}