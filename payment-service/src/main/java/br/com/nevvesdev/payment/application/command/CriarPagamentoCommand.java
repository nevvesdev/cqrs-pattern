package br.com.nevvesdev.payment.application.command;

import java.math.BigDecimal;

public record CriarPagamentoCommand(
        String contaOrigem,
        String contaDestino,
        BigDecimal valor
) {}