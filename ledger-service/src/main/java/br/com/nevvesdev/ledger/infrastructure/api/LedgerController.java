package br.com.nevvesdev.ledger.infrastructure.api;

import br.com.nevvesdev.ledger.application.query.BuscarExtratoConta;
import br.com.nevvesdev.ledger.domain.model.LancamentoLedger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ledger")
@Tag(name = "Ledger", description = "Consulta de extrato por conta")
public class LedgerController {

    private final BuscarExtratoConta buscarExtratoConta;

    public LedgerController(BuscarExtratoConta buscarExtratoConta) {
        this.buscarExtratoConta = buscarExtratoConta;
    }

    @GetMapping("/{numeroConta}")
    @Operation(summary = "Busca o extrato de uma conta")
    public ResponseEntity<RespostaExtrato> buscar(@PathVariable String numeroConta) {
        var lancamentos = buscarExtratoConta.handle(numeroConta);

        if (lancamentos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RespostaExtrato.de(numeroConta, lancamentos));
    }

    record RespostaLancamento(
            UUID id,
            UUID pagamentoId,
            String contaOrigem,
            String contaDestino,
            BigDecimal valor,
            String status,
            Instant processadoEm
    ) {
        static RespostaLancamento de(LancamentoLedger lancamento) {
            return new RespostaLancamento(
                    lancamento.getId(),
                    lancamento.getPagamentoId(),
                    lancamento.getContaOrigem(),
                    lancamento.getContaDestino(),
                    lancamento.getValor(),
                    lancamento.getStatus(),
                    lancamento.getProcessadoEm()
            );
        }
    }

    record RespostaExtrato(
            String numeroConta,
            int totalLancamentos,
            BigDecimal saldoTotal,
            List<RespostaLancamento> lancamentos
    ) {
        static RespostaExtrato de(String numeroConta, List<LancamentoLedger> lancamentos) {
            var itens = lancamentos.stream().map(RespostaLancamento::de).toList();

            var saldo = lancamentos.stream()
                    .map(l -> l.getContaDestino().equals(numeroConta)
                            ? l.getValor()
                            : l.getValor().negate())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new RespostaExtrato(numeroConta, lancamentos.size(), saldo, itens);
        }
    }
}