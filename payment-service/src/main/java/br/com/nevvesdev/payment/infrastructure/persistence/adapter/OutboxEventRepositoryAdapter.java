package br.com.nevvesdev.payment.infrastructure.persistence.adapter;

import br.com.nevvesdev.payment.domain.model.OutboxEvent;
import br.com.nevvesdev.payment.domain.port.OutboxEventRepository;
import br.com.nevvesdev.payment.infrastructure.persistence.entity.OutboxEventEntity;
import br.com.nevvesdev.payment.infrastructure.persistence.repository.OutboxEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OutboxEventRepositoryAdapter implements OutboxEventRepository {

    private final OutboxEventJpaRepository jpaRepository;

    public OutboxEventRepositoryAdapter(OutboxEventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public OutboxEvent salvar(OutboxEvent evento) {
        var entity = OutboxEventEntity.de(evento);
        jpaRepository.save(entity);
        return evento;
    }

    @Override
    public List<OutboxEvent> buscarNaoProcessados() {
        return jpaRepository.findByProcessadoFalse()
                .stream()
                .map(OutboxEventEntity::toDomain)
                .toList();
    }

    @Override
    public OutboxEvent atualizar(OutboxEvent evento) {
        var entity = OutboxEventEntity.de(evento);
        jpaRepository.save(entity);
        return evento;
    }
}