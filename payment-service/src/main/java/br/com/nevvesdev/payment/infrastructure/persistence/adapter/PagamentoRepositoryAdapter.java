package br.com.nevvesdev.payment.infrastructure.persistence.adapter;

import br.com.nevvesdev.payment.domain.model.Payment;
import br.com.nevvesdev.payment.domain.port.PaymentRepository;
import br.com.nevvesdev.payment.infrastructure.persistence.entity.PagamentoEntity;
import br.com.nevvesdev.payment.infrastructure.persistence.repository.PagamentoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PagamentoRepositoryAdapter implements PaymentRepository {

    private final PagamentoJpaRepository jpaRepository;

    public PagamentoRepositoryAdapter(PagamentoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment salvar(Payment pagamento) {
        var entity = PagamentoEntity.de(pagamento);
        jpaRepository.save(entity);
        return pagamento;
    }

    @Override
    public Optional<Payment> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(PagamentoEntity::toDomain);
    }
}