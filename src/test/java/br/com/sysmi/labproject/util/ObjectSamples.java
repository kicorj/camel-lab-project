package br.com.sysmi.labproject.util;

import java.util.ArrayList;

import br.com.sysmi.labproject.model.Compra;
import br.com.sysmi.labproject.model.Item;

public class ObjectSamples {
	
	/**
	   * Retorna uma lista com uma �nica 'Compra' sem 'Items' para teste.
	   * 
	   * @return
	   */
	  public static ArrayList<Compra> getCompraMocked() {
		  ArrayList<Compra> compras = new ArrayList<Compra>();
		  
		  Compra compra = new Compra();
	      compra.setIdCompra(123);
	      compra.setNomeComprador("Marcelo Lima");
	      compra.setIdentidade(12345);
	      compras.add(compra);
	    
	      return compras;
	  }
	  
	  /**
	   * Retorna uma lista de itens para teste.
	   * 
	   * @return
	   */
	  public static ArrayList<Item> getItemsMocked() {
		Item item1 = new Item();
	  	item1.setItemId(123);
	  	item1.setSigla("BCK");
	  	item1.setDescricao("Bicicleta");
	  	item1.setPreco(125.00);
	      item1.setQuantidade(1);
	      
	    Item item2 = new Item();
	  	item2.setItemId(222);
	  	item2.setSigla("TRD");
	  	item2.setDescricao("Torradeira");
	  	item2.setPreco(15.00);
	    item2.setQuantidade(3);
	    
	    Item item3 = new Item();
	  	item3.setItemId(546);
	  	item3.setSigla("APR");
	  	item3.setDescricao("Analise Preliminar de Risco");
	  	item3.setPreco(890.50);
	    item3.setQuantidade(1);
	      
	    Item item4 = new Item();
	    item4.setItemId(698);
	    item4.setSigla("GTF");
	    item4.setDescricao("Gran tielt fertiot");
	    item4.setPreco(65.78);
	    item4.setQuantidade(7);
	        
	    Item item5 = new Item();
	    item5.setItemId(784);
	    item5.setSigla("OTT");
	    item5.setDescricao("Cabo OTT");
	    item5.setPreco(224.35);
	    item5.setQuantidade(9);
	      
	    ArrayList<Item> items = new ArrayList<Item>();
	    items.add(item1);
	    items.add(item2);
	    items.add(item3);
	    items.add(item4);
	    items.add(item5);
	      
	    return items;
	  }
}