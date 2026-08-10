package br.com.nevvesdev.payment.infrastructure.persistence.entity;

import br.com.nevvesdev.payment.domain.model.Payment;
import br.com.nevvesdev.payment.domain.model.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
public class PagamentoEntity {

    @Id
    private UUID id;

    @Column(name = "conta_origem", nullable = false)
    private String contaOrigem;

    @Column(name = "conta_destino", nullable = false)
    private String contaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    public PagamentoEntity() {}

    public static PagamentoEntity de(Payment pagamento) {
        var entity = new PagamentoEntity();
        entity.id = pagamento.getId();
        entity.contaOrigem = pagamento.getContaOrigem();
        entity.contaDestino = pagamento.getContaDestino();
        entity.valor = pagamento.getValor();
        entity.status = pagamento.getStatus();
        entity.criadoEm = pagamento.getCriadoEm();
        return entity;
    }

    public Payment toDomain() {
        return new Payment(id, contaOrigem, contaDestino, valor, status, criadoEm);
    }

    public UUID getId() { return id; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public BigDecimal getValor() { return valor; }
    public PaymentStatus getStatus() { return status; }
    public Instant getCriadoEm() { return criadoEm; }
}