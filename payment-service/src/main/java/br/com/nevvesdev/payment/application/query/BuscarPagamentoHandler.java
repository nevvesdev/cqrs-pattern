package br.com.nevvesdev.payment.application.query;

import br.com.nevvesdev.payment.domain.model.Payment;
import br.com.nevvesdev.payment.domain.port.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarPagamentoHandler {

    private final PaymentRepository paymentRepository;

    public BuscarPagamentoHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> handle(UUID id) {
        return paymentRepository.buscarPorId(id);
    }
}