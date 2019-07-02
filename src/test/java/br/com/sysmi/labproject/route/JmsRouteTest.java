package br.com.sysmi.labproject.route;

import static org.junit.Assert.fail;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.sysmi.labproject.util.JsonSamples;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MockEndpointsAndSkip
@ContextConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class JmsRouteTest {
	
  @Autowired
  CamelContext camelContext;
  
  @Produce(uri=JMSRoute.URI)
  ProducerTemplate template;
  
  @EndpointInject(uri="mock://db-route-post")
  private MockEndpoint mockDbRoutePost;
  
  @PropertyInject(value="{{route.jms.id}}")
  private String routeJmsId;
  
  @Before
  public void prepareTest() throws Exception {
	  mockDbRoutePost.reset();
	  interceptRoutes();
  }
  
  private void interceptRoutes() throws Exception {
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(DatabaseRoute.INSERT_ID)
					.skipSendToOriginalEndpoint()
					.log("---------- NOT SEND TO Camel DATABASE ----------")
					.to(mockDbRoutePost);
			}
			
	};
	camelContext.getRouteDefinition(routeJmsId).adviceWith(camelContext, routeBuilder);
  }

  
  /**
   * M�todo verifica se o arquivo Json vazio � ignorado pela rota
   * e n�o enviado ao banco de dados.
   */
  @Test
  public void testCompraVazia() {

	mockDbRoutePost.expectedMessageCount(0);
		
    try {
      template.sendBody(JMSRoute.URI, JsonSamples.getCOMPRA_VAZIA());

      mockDbRoutePost.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se o arquivo Json com conte�do inv�lido 
   * � ignorado pela rota e n�o enviado ao banco de dados.
   */
  @Test
  public void testCompraInvalida() {

	mockDbRoutePost.expectedMessageCount(0);
		
    try {
      template.sendBody(JMSRoute.URI, JsonSamples.getCOMPRA_INVALIDA());

      mockDbRoutePost.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
}