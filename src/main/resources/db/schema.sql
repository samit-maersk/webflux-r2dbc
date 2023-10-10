CREATE TABLE employee (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    address         VARCHAR(100) NOT NULL,
    designation     VARCHAR(100) NOT NULL,
    salary          FLOAT NOT NULL,
    doj             DATE NOT NULL,
    department      VARCHAR(100) NOT NULL,
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    created_at      DATE,
    updated_at      DATE
);