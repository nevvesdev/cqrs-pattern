CREATE TABLE lancamentos_ledger (
    id            UUID           PRIMARY KEY,
    pagamento_id  UUID           NOT NULL UNIQUE,
    conta_origem  VARCHAR(64)    NOT NULL,
    conta_destino VARCHAR(64)    NOT NULL,
    valor         NUMERIC(19, 2) NOT NULL,
    status        VARCHAR(20)    NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_lancamentos_conta_origem  ON lancamentos_ledger (conta_origem);
CREATE INDEX idx_lancamentos_conta_destino ON lancamentos_ledger (conta_destino);