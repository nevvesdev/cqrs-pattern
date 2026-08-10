package br.com.nevvesdev.ledger.application.query;

import br.com.nevvesdev.ledger.domain.model.LancamentoLedger;
import br.com.nevvesdev.ledger.infrastructure.persistence.adapter.LancamentoLedgerRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarExtratoConta {

    private final LancamentoLedgerRepositoryAdapter repositoryAdapter;

    public BuscarExtratoConta(LancamentoLedgerRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    public List<LancamentoLedger> handle(String numeroConta) {
        return repositoryAdapter.buscarPorConta(numeroConta);
    }
}