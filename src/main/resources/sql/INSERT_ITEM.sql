INSERT CAMEL_LAB.ITEM (itemId, idCompra, sigla, descricao, preco, quantidade)
VALUES (:#${body.itemId},
        :#${property.idCompra},
		:#${body.sigla},
		:#${body.descricao},
		:#${body.preco},
		:#${body.quantidade});