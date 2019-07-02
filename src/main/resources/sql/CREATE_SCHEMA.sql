-- -----------------------------------------------------
-- Schema CAMEL_LAB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS CAMEL_LAB DEFAULT CHARACTER SET utf8;

USE CAMEL_LAB;

-- -----------------------------------------------------
-- Table CAMEL_LAB.COMPRA
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS COMPRA (
idCompra INT NOT NULL,
nomeComprador VARCHAR(100) NULL,
identidade INT NULL,
total FLOAT NULL
PRIMARY KEY (idCompra)
);

-- -----------------------------------------------------
-- Table CAMEL_LAB.ITEM
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ITEM (
itemId INT NOT NULL,
idCompra INT NOT NULL,
sigla VARCHAR(3) NULL,
descricao VARCHAR(100) NULL,
preco FLOAT NULL,
quantidade INT NULL,
PRIMARY KEY (itemId)
);

-- -----------------------------------------------------
-- Foreign key CAMEL_LAB.ITEM to CAMEL_LAB.COMPRA
-- -----------------------------------------------------
ALTER TABLE ITEM ADD FOREIGN KEY (idCompra) REFERENCES COMPRA(idCompra);

-- -----------------------------------------------------
-- Foreign key CAMEL_LAB.ITEM to CAMEL_LAB.COMPRA
-- Check need according to project.
-- -----------------------------------------------------
-- ALTER TABLE ITEM ADD FOREIGN KEY (idCompra) REFERENCES COMPRA(idCompra) ON DELETE CASCADE;