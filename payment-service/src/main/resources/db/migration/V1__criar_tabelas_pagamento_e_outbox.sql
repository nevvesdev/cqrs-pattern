CREATE TABLE pagamentos (
    id          UUID        PRIMARY KEY,
    conta_origem   VARCHAR(64) NOT NULL,
    conta_destino  VARCHAR(64) NOT NULL,
    valor       NUMERIC(19, 2) NOT NULL,
    status      VARCHAR(20) NOT NULL,
    criado_em   TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE outbox_events (
    id            UUID        PRIMARY KEY,
    tipo_agregado VARCHAR(64) NOT NULL,
    id_agregado   UUID        NOT NULL,
    tipo_evento   VARCHAR(64) NOT NULL,
    payload       TEXT        NOT NULL,
    processado    BOOLEAN     NOT NULL DEFAULT FALSE,
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_outbox_nao_processados ON outbox_events (processado)
    WHERE processado = FALSE;