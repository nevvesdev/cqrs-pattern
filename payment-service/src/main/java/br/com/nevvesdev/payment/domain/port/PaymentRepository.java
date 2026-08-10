package br.com.nevvesdev.payment.domain.port;

import br.com.nevvesdev.payment.domain.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment salvar(Payment pagamento);
    Optional<Payment> buscarPorId(UUID id);
}