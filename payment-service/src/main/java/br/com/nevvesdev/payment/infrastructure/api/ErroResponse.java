package br.com.nevvesdev.payment.infrastructure.api;

import java.time.Instant;
import java.util.List;

public record ErroResponse(
        int status,
        String erro,
        List<String> mensagens,
        Instant timestamp
) {
    static ErroResponse de(int status, String erro, List<String> mensagens) {
        return new ErroResponse(status, erro, mensagens, Instant.now());
    }

    static ErroResponse de(int status, String erro, String mensagem) {
        return de(status, erro, List.of(mensagem));
    }
}