package br.com.sysmi.labproject.route;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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

import br.com.sysmi.labproject.model.Compra;
import br.com.sysmi.labproject.util.ObjectSamples;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MockEndpointsAndSkip
@ContextConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class DatabaseRouteTest {
	
  @Autowired
  CamelContext camelContext;
  
  @Produce(uri=DatabaseRoute.INSERT_URI)
  ProducerTemplate templatePost;
  
  @Produce(uri=DatabaseRoute.SQL_SELECT_COMPRA_BY_ID)
  ProducerTemplate templateGetById;
  
  @EndpointInject(uri="mock://db-compra")
  private MockEndpoint mockDbCompra;
  
  @EndpointInject(uri="mock://db-item")
  private MockEndpoint mockDbItem;
  
  @PropertyInject(value="{{route.db.select.id}}")
  private String routeGetId;
  
  @PropertyInject(value="{{route.db.insert.id}}")
  private String routePostId;
  
  @Before
  public void prepareTest() throws Exception {
	  mockDbCompra.reset();
	  mockDbItem.reset();
	  
	  interceptRouteGet();
	  interceptRoutePost();
  }
  
  private void interceptRoutePost() throws Exception {
	  
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(DatabaseRoute.SQL_INSERT_COMPRA)
			      .skipSendToOriginalEndpoint()
				  .log("---------- NOT SEND TO Camel DATABASE = Post Table Compra ----------")
				  .to(mockDbCompra);
				
				interceptSendToEndpoint(DatabaseRoute.SQL_INSERT_ITEM)
				  .skipSendToOriginalEndpoint()
				  .log("---------- NOT SEND TO Camel DATABASE = Post Table Item ----------")
				  .to(mockDbItem);
			}
			
	};
	camelContext.getRouteDefinition(routePostId).adviceWith(camelContext, routeBuilder);
  }

  private void interceptRouteGet() throws Exception {
	  
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint(DatabaseRoute.SQL_SELECT_COMPRA_BY_ID+"*")
			      .skipSendToOriginalEndpoint()
				  .log("---------- NOT SEND TO Camel DATABASE = Get Table Compra ----------")
				  .to(mockDbCompra);
				
				interceptSendToEndpoint(DatabaseRoute.SQL_SELECT_COMPRA_BY_ID+"*")
				  .skipSendToOriginalEndpoint()
				  .log("---------- NOT SEND TO Camel DATABASE = Get Table Item ----------")
				  .to(mockDbItem);
			}
			
	};
	camelContext.getRouteDefinition(routeGetId).adviceWith(camelContext, routeBuilder);
}
  
  
  /**
   * M�todo verifica se o objeto Compra recebido
   * sem Items passa corretamente pelos procedimentos da rota.
   */
  @Test
  public void testPostCompraSemItems() {
	  
    mockDbCompra.expectedMessageCount(0);
	mockDbItem.expectedMessageCount(0);
	
    try {
      // Mocka uma Compra sem Items.
      ArrayList<Compra> compras = ObjectSamples.getCompraMocked();
      Compra compra = compras.get(0);  
        
      templatePost.sendBody(DatabaseRoute.INSERT_URI,compra);
      
      mockDbCompra.assertIsSatisfied();
      mockDbItem.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se o valor null recebido
   * passa corretamente pelos procedimentos da rota.
   */
  @Test
  public void testPostCompraVazia() {
	
	mockDbCompra.expectedMessageCount(0);
    mockDbItem.expectedMessageCount(0);
		
    try {
      // Passa nenhum objeto no Body.
      templatePost.sendBody(DatabaseRoute.INSERT_URI,null);
        
      mockDbCompra.assertIsSatisfied();
      mockDbItem.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se um objeto diferente do objeto Compra 
   * passa corretamente pelos procedimentos da rota.
   */
  @Test
  public void testPostCompraInvalida() {
	  
	mockDbCompra.expectedMessageCount(0);
    mockDbItem.expectedMessageCount(0);
    
    try {
      // Passa um objeto n�o esperado no Body.
      templatePost.sendBody(DatabaseRoute.INSERT_URI,new Object());
        
      mockDbCompra.assertIsSatisfied();
      mockDbItem.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
  /**
   * M�todo verifica se a rota de banco de dados
   * responde com o objeto Json correto sem Items.
   */
  @Test
  public void testGetCompraSemItems() {
	mockDbCompra.expectedMessageCount(0);
	mockDbItem.expectedMessageCount(0);
	  
    try {
    	// Mocka somente uma Compra sem Items.
    	mockDbCompra.whenAnyExchangeReceived(
    	new Processor(){
            public void process(Exchange exchange) throws Exception {
          	  exchange.getIn().setBody(ObjectSamples.getCompraMocked());
            } 
        });

    	templateGetById.sendBodyAndHeader(DatabaseRoute.SELECT_URI,"","id",0);
      
      mockDbCompra.assertIsSatisfied();
      mockDbItem.assertIsSatisfied();
    }
    catch(Exception cee) {
      fail();
    }
  }
  
}