INSERT INTO CAMEL_LAB.COMPRA (idCompra, nomeComprador, identidade, total)
VALUES (:#${body.idCompra},
		:#${body.nomeComprador},
		:#${body.identidade},
		:#${property.total});