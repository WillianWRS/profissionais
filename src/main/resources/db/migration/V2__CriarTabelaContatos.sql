CREATE TABLE contatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR NOT NULL,
    contato VARCHAR NOT NULL,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE,
    profissional_id INTEGER NOT NULL,
    FOREIGN KEY (profissional_id) REFERENCES profissionais (id)
);