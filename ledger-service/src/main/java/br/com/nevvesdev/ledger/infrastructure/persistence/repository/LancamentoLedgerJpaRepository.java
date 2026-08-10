package br.com.nevvesdev.ledger.infrastructure.persistence.repository;

import br.com.nevvesdev.ledger.infrastructure.persistence.entity.LancamentoLedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LancamentoLedgerJpaRepository extends JpaRepository<LancamentoLedgerEntity, UUID> {
    List<LancamentoLedgerEntity> findByContaOrigemOrContaDestino(String contaOrigem, String contaDestino);
    boolean existsByPagamentoId(UUID pagamentoId);
}