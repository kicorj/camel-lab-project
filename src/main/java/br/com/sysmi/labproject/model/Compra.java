package br.com.sysmi.labproject.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Compra {

	private Integer idCompra;
	private String nomeComprador;
	private Integer identidade;
	private List<Item> items;

	public static final double minimumTotalAllowed = 300D;
	
	public Compra() {
		super();
	}
	@Override
	public String toString() {
		return "Compra [idCompra=" + idCompra + ", nomeComprador=" + nomeComprador + ", identidade=" + identidade
				+ ", items=" + items + "]";
	}
	public Integer getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}
	public String getNomeComprador() {
		return nomeComprador;
	}
	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}
	public Integer getIdentidade() {
		return identidade;
	}
	public void setIdentidade(Integer identidade) {
		this.identidade = identidade;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Double getTotalOrder() {
		Double total = items == null ? null : items.stream().mapToDouble(Item::getSubTotal).sum();
		
		// regra de desconto
		if(total > 1000) {
			total = total * .9;
		}
		
		return total;
	}
	
	public Double getMinimumTotalAllowed() {
		return minimumTotalAllowed;
	}
}
