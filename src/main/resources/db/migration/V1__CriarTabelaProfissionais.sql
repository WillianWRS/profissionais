CREATE TABLE profissionais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR NOT NULL,
    cargo VARCHAR NOT NULL CHECK (cargo IN ('Desenvolvedor', 'Designer', 'Suporte', 'Tester')),
    nascimento DATE NOT NULL,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(7) default 'Ativo' NOT NULL CHECK (status IN ('Ativo', 'Inativo')),
    deleted_date DATE
);