package br.com.nevvesdev.payment.domain.port;

import br.com.nevvesdev.payment.domain.model.OutboxEvent;

import java.util.List;

public interface OutboxEventRepository {
    OutboxEvent salvar(OutboxEvent evento);
    List<OutboxEvent> buscarNaoProcessados();
    OutboxEvent atualizar(OutboxEvent evento);
}