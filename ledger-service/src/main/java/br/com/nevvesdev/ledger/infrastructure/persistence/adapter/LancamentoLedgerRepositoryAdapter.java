package br.com.nevvesdev.ledger.infrastructure.persistence.adapter;

import br.com.nevvesdev.ledger.domain.model.LancamentoLedger;
import br.com.nevvesdev.ledger.infrastructure.persistence.entity.LancamentoLedgerEntity;
import br.com.nevvesdev.ledger.infrastructure.persistence.repository.LancamentoLedgerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LancamentoLedgerRepositoryAdapter {

    private final LancamentoLedgerJpaRepository jpaRepository;

    public LancamentoLedgerRepositoryAdapter(LancamentoLedgerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public LancamentoLedger salvar(LancamentoLedger lancamento) {
        var entity = LancamentoLedgerEntity.de(lancamento);
        jpaRepository.save(entity);
        return lancamento;
    }

    public List<LancamentoLedger> buscarPorConta(String numeroConta) {
        return jpaRepository
                .findByContaOrigemOrContaDestino(numeroConta, numeroConta)
                .stream()
                .map(LancamentoLedgerEntity::toDomain)
                .toList();
    }

    public boolean jaProcessado(UUID pagamentoId) {
        return jpaRepository.existsByPagamentoId(pagamentoId);
    }
}