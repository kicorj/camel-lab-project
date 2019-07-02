package br.com.sysmi.labproject.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import br.com.sysmi.labproject.model.Compra;

/**
 * Rotas de Arquivos
 * @author FranciscoCardoso
 *
 */
@Component
public class FileRoute extends RouteBuilder {

	private static final String ID = "{{route.file.id}}";
	private static final String URI = "{{route.file.uri}}";
	
	@Override
	public void configure() throws Exception {
		from(URI)
			.routeId(ID)
			.doTry()
				.unmarshal().json(JsonLibrary.Jackson, Compra.class)
			.endDoTry()
			.doCatch(Exception.class)
				.log("Pedido em formato inválido")
				.stop()
			.end()
			.to(DatabaseRoute.INSERT_URI)
		.end();
	}

}
