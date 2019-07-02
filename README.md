# Camel Lab Project

Projeto para o treinamento de Apache Camel.

Escopo:

Construir uma plataforma que receba pedidos em formato JSON  através de 2 rotas: Arquivo e Fila JMS.

Os pedidos serão encaminhados para uma rota que irá gravar em banco de dados MySQL

Expor através da DSL REST, 2 endpoints:

	http://localhost:8080/camel-lab/api/compras - Retornar os dados de todas as compras
	http://localhost:8080/camel-lab/api/compras/{idCompra} - Retornar os dados da compra passada por parâmetro
	
Regras:

Compras mínimas de 300,00.

Compras com valor total >= 1000,00 recebem 10% de desconto. 

Não aceitar produtos com as siglas PRR, PT e TRD.

### Building

O projeto pode ser instalado coom

    mvn clean install


# camel-lab-project
