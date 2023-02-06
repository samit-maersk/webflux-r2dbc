CREATE TABLE employee (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    address     VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL,
    salary      FLOAT NOT NULL,
    doj         DATE NOT NULL,
    department  VARCHAR(100) NOT NULL
);