package br.com.sysmi.labproject.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

	private static final String ID = "{{route.file.id}}";
	private static final String URI = "{{route.file.uri}}";
	
	@Override
	public void configure() throws Exception {
		from(URI)
			.routeId(ID)
			.to(DatabaseRoute.INSERT_URI)
		.end();
	}

}
