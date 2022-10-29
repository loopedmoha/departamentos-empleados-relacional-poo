CREATE TABLE IF NOT EXISTS departamentos (
    uuid UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    presupuesto NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS empleados (
    uuid UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nacimiento VARCHAR(10) NOT NULL,
    departamentoId UUID,
    foreign KEY (departamentoId) references departamentos (uuid)
);
