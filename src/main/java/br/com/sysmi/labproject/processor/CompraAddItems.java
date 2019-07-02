package br.com.sysmi.labproject.processor;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import br.com.sysmi.labproject.model.Item;
import br.com.sysmi.labproject.model.Compra;

@Component
public class CompraAddItems implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ArrayList<Item> items = ((ArrayList<Item>)exchange.getIn().getBody());
		Compra order = (Compra)exchange.getProperty("compra");
		order.setItems(items);
		
		exchange.getIn().setBody(order);
		exchange.setProperty("compra", null);
		
	}

}
