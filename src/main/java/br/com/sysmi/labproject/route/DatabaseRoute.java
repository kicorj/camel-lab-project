package br.com.sysmi.labproject.route;

import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.com.sysmi.labproject.model.Compra;
import br.com.sysmi.labproject.model.Item;
import br.com.sysmi.labproject.processor.CompraAddItems;

/**
 * Rotas de Banco de dados
 * @author FranciscoCardoso
 *
 */
@Component
public class DatabaseRoute extends RouteBuilder {

	public static final String SELECT_URI = "{{route.db.select.uri}}";
	public static final String SELECT_ID = "{{route.db.select.id}}";
	public static final String SQL_SELECT_COMPRA = "{{sql.select.compra.uri}}";
	public static final String SQL_SELECT_COMPRA_BY_ID = "{{sql.select.comprabyid.uri}}";
	public static final String SQL_SELECT_ITEM = "{{sql.select.item.uri}}";
	
	public static final String INSERT_URI = "{{route.db.insert.uri}}";
	public static final String INSERT_ID = "{{route.db.insert.id}}";
	public static final String SQL_INSERT_COMPRA = "{{sql.insert.compra.uri}}";
	public static final String SQL_INSERT_ITEM = "{{sql.insert.item.uri}}";
	
	@Autowired
	private CompraAddItems compraAddItems;
	
	@Override
	public void configure() throws Exception {
		
		from(SELECT_URI)
		.id(SELECT_ID)
		.choice()
			.when(PredicateBuilder.isNull(simple("${header.idCompra}")))
				.to(SQL_SELECT_COMPRA + "&outputClass=" + Compra.class.getName())
			.otherwise()
				.to(SQL_SELECT_COMPRA_BY_ID + "&outputClass=" + Compra.class.getName())
			.end()
		.end()
		.choice()
        .when(PredicateBuilder.isNotEqualTo(simple("${body.size}"), constant(0)))
	        .split(body())
	        .setProperty("compra",simple("${body}"))
		    .to(SQL_SELECT_ITEM + "&outputClass="+Item.class.getName())
		    .choice()
		    	.when(PredicateBuilder.isNotEqualTo(simple("${body.size}"), constant(0)))
				    .setBody(simple("${body}"))
				    .process(compraAddItems) 
			    .otherwise()
			    	.setBody(simple("${exchangeProperty.compra}"))
			    .end()
			.endChoice()
		.endChoice()
		.end();
		
		from(INSERT_URI)
		.id(INSERT_ID)
		.doTry()
			.choice().when(PredicateBuilder.isNotEqualTo((simple("${body.idCompra}")), constant(null)))
				.setProperty("total",simple("${body.totalOrder}"))
				.choice().when(PredicateBuilder.isLessThan((simple("${property.total}")), simple("${body.minimumTotalAllowed}")))
					.log("Só é permitido compras acimas de ${body.minimumTotalAllowed}")
					.stop()
				.endChoice()
				.to(SQL_INSERT_COMPRA)
				.choice().when(PredicateBuilder.isNotEqualTo((simple("${body.items}")), constant(null)))
					.setProperty("idCompra",simple("${body.idCompra}"))
					.split(simple("${body.items}")).parallelProcessing(true)
				    	.choice()
				    		.when(simple("${body.isNotAllowedItem} == true"))
				    			.log("Produto não permitido - ${body.sigla}")
				    		.otherwise()
				    			.to(SQL_INSERT_ITEM)
				    		.end()
				    	.endChoice()
				    .end()
			    .endChoice()
		    .endChoice()
	    .endDoTry()
	    .doCatch(DuplicateKeyException.class)
			.log("{{route.db.insert.pk_error_message}}")
		    .log("${exception.message}")
			.stop()
		.doCatch(Exception.class)
			.log("{{route.db.insert.generic_error_message}}")
			.log("${exception.message}")
			.stop()
		.end()
		.end();
	}

}
