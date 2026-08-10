package br.com.nevvesdev.payment.infrastructure.api;

import br.com.nevvesdev.payment.application.command.CriarPagamentoCommand;
import br.com.nevvesdev.payment.application.command.CriarPagamentoHandler;
import br.com.nevvesdev.payment.application.query.BuscarPagamentoHandler;
import br.com.nevvesdev.payment.domain.model.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Criação e consulta de pagamentos")
public class PagamentoController {

    private final CriarPagamentoHandler criarPagamentoHandler;
    private final BuscarPagamentoHandler buscarPagamentoHandler;

    public PagamentoController(
            CriarPagamentoHandler criarPagamentoHandler,
            BuscarPagamentoHandler buscarPagamentoHandler
    ) {
        this.criarPagamentoHandler = criarPagamentoHandler;
        this.buscarPagamentoHandler = buscarPagamentoHandler;
    }

    @PostMapping
    @Operation(summary = "Cria um novo pagamento")
    public ResponseEntity<Void> criar(@Valid @RequestBody RequisicaoPagamento requisicao) {
        UUID id = criarPagamentoHandler.handle(new CriarPagamentoCommand(
                requisicao.contaOrigem(),
                requisicao.contaDestino(),
                requisicao.valor()
        ));
        return ResponseEntity.created(URI.create("/pagamentos/" + id)).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pagamento pelo ID")
    public ResponseEntity<RespostaPagamento> buscar(@PathVariable UUID id) {
        return buscarPagamentoHandler.handle(id)
                .map(RespostaPagamento::de)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    record RequisicaoPagamento(
            @NotBlank String contaOrigem,
            @NotBlank String contaDestino,
            @NotNull @Positive BigDecimal valor
    ) {}

    record RespostaPagamento(
            UUID id,
            String contaOrigem,
            String contaDestino,
            BigDecimal valor,
            String status,
            Instant criadoEm
    ) {
        static RespostaPagamento de(Payment pagamento) {
            return new RespostaPagamento(
                    pagamento.getId(),
                    pagamento.getContaOrigem(),
                    pagamento.getContaDestino(),
                    pagamento.getValor(),
                    pagamento.getStatus().name(),
                    pagamento.getCriadoEm()
            );
        }
    }
}