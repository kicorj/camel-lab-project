package br.com.sysmi.labproject.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * Rotas Rest
 * 
 * Expõe 2 enpoints REST:
* 
* 		/camel-lab/api/compras - Recupera no banco de dados e retorna todas as compras
* 		camel-lab/api/compras/{idCompra} - Recupera no banco de dados e retorna a compra a partir de um Id
* 
 * @author FranciscoCardoso
 *
 */
@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		restConfiguration()
		  .contextPath("/*") 
		  .port("8080")
		  .enableCORS(true)
		  .component("servlet")
		  .bindingMode(RestBindingMode.json);
		
		rest("/camel-lab/api/compras")
			.get("/").consumes("application/json").to(DatabaseRoute.SELECT_URI)
			.get("/{idCompra}").consumes("application/json").to(DatabaseRoute.SELECT_URI);
	}

}
