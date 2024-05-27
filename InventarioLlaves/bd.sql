DROP DATABASE IF EXISTS inventarioLlaves;
CREATE DATABASE IF NOT EXISTS inventarioLlaves;
USE inventarioLlaves;

CREATE TABLE llave (
    codigo INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) UNIQUE NOT NULL,
    precio DOUBLE NOT NULL,
    inventario INT NOT NULL,
    PRIMARY KEY (codigo)
);

INSERT INTO llave (nombre, precio, inventario) VALUES ("Llave Ejemplo", 15.00, 15);
INSERT INTO llave (nombre, precio, inventario) VALUES ("Llave M16P1", 85.00, 100);
/* Consultas */

select * FROM llave;

CREATE USER 'mecanico'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON inventarioLlaves.* TO 'mecanico'@'localhost';
FLUSH PRIVILEGES;