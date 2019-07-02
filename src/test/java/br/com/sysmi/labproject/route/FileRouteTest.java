package br.com.sysmi.labproject.route;

import static org.junit.Assert.fail;

import java.io.File;

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


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MockEndpointsAndSkip
@ContextConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class FileRouteTest {
	
  @Autowired
  CamelContext camelContext;
  
  @Produce(uri=FileRoute.URI)
  ProducerTemplate template;
  
  @EndpointInject(uri="mock://db-route-post")
  private MockEndpoint mockDbRoutePost;
  
  @PropertyInject(value="{{route.file.id}}")
  private String routeFileId;
  
  @Before
  public void prepareTest() throws Exception {
	  mockDbRoutePost.reset();
	  interceptRoutes();
  }
  
  private void interceptRoutes() throws Exception {
	  
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(DatabaseRoute.INSERT_URI)
					.skipSendToOriginalEndpoint()
					.log("---------- NOT SEND TO Camel DATABASE ----------")
					.to(mockDbRoutePost);
			}
			
	};
	camelContext.getRouteDefinition(routeFileId).adviceWith(camelContext, routeBuilder);
  }

  /**
   * M�todo verifica se o arquivo Json recebido no file system
   * est� sendo convertido corretamente para o Bean 'Compra'
   * e enviado � rota de banco de dados.
   */
  @Test
  public void testCompraOk() {

	mockDbRoutePost.expectedMessageCount(1);
	
    try {
      template.sendBody(FileRoute.URI,new File("src/test/resources/compra_ok.json"));
      
      mockDbRoutePost.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se o arquivo Json recebido no file system
   * est� sendo convertido corretamente para o Bean 'Compra'
   * mesmo sem dados de Items e enviado � rota de banco de dados.
   */
  @Test
  public void testCompraSemItems() {
	  
	mockDbRoutePost.expectedMessageCount(1);
	
    try {
      template.sendBody(FileRoute.URI,new File("src/test/resources/compra_sem_items.json"));
      
      mockDbRoutePost.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se o arquivo Json vazio � ignorado pela rota
   * e n�o enviado ao banco de dados.
   */
  @Test
  public void testCompraVazio() {

	mockDbRoutePost.expectedMessageCount(0);
	
    try {
      template.sendBody(FileRoute.URI,new File("src/test/resources/compra_vazia.json"));
      
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
      template.sendBody(FileRoute.URI,new File("src/test/resources/compra_invalida.json"));
      
      mockDbRoutePost.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
}