package br.com.nevvesdev.payment.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final String contaOrigem;
    private final String contaDestino;
    private final BigDecimal valor;
    private PaymentStatus status;
    private final Instant criadoEm;

    public Payment(
            UUID id,
            String contaOrigem,
            String contaDestino,
            BigDecimal valor,
            PaymentStatus status,
            Instant criadoEm
    ) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.status = status;
        this.criadoEm = criadoEm;
    }

    public static Payment criar(
            String contaOrigem,
            String contaDestino,
            BigDecimal valor
    ) {
        return new Payment(
                UUID.randomUUID(),
                contaOrigem,
                contaDestino,
                valor,
                PaymentStatus.PENDENTE,
                Instant.now()
        );
    }

    public void confirmar() {
        this.status = PaymentStatus.CONFIRMADO;
    }

    public void falhar() {
        this.status = PaymentStatus.FALHOU;
    }

    public UUID getId() { return id; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public BigDecimal getValor() { return valor; }
    public PaymentStatus getStatus() { return status; }
    public Instant getCriadoEm() { return criadoEm; }
}