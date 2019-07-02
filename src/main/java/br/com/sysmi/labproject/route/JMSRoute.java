package br.com.sysmi.labproject.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JMSRoute extends RouteBuilder {

	public static final String ID = "{{route.jms.id}}";
	public static final String URI = "{{route.jms.uri}}";
	
	@Override
	public void configure() throws Exception {
		from(URI)
			.routeId(ID)
			.to(DatabaseRoute.INSERT_URI)
		.end();
	}
}
