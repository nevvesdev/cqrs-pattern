package br.com.nevvesdev.payment.infrastructure.persistence.repository;

import br.com.nevvesdev.payment.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, UUID> {}