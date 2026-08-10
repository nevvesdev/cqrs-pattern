package br.com.nevvesdev.payment.application.command;

import br.com.nevvesdev.payment.domain.model.OutboxEvent;
import br.com.nevvesdev.payment.domain.model.Payment;
import br.com.nevvesdev.payment.domain.port.OutboxEventRepository;
import br.com.nevvesdev.payment.domain.port.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CriarPagamentoHandler {

    private final PaymentRepository paymentRepository;
    private final OutboxEventRepository outboxEventRepository;

    public CriarPagamentoHandler(
            PaymentRepository paymentRepository,
            OutboxEventRepository outboxEventRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional
    public UUID handle(CriarPagamentoCommand comando) {
        var pagamento = Payment.criar(
                comando.contaOrigem(),
                comando.contaDestino(),
                comando.valor()
        );

        paymentRepository.salvar(pagamento);

        var evento = OutboxEvent.de(pagamento);
        outboxEventRepository.salvar(evento);

        return pagamento.getId();
    }
}