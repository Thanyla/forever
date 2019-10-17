CREATE TABLE presentes (
    id BIGINT NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    valor DOUBLE NOT NULL,
    categoria VARCHAR(255),
    CONSTRAINT presentes_pkey PRIMARY KEY (id)
)