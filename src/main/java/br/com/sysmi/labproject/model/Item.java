package br.com.sysmi.labproject.model;

import java.util.Arrays;
import java.util.List;

public class Item {
	private Integer itemId;
	private String sigla;
	private String descricao;
	private Double preco;
	private Integer quantidade;
	
	private static final List<String> NOT_ALLOWED_ITEMS = Arrays.asList("PRR","PT","TRD");
	
	public Item() {
		super();
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", sigla=" + sigla + ", descricao=" + descricao + ", preco=" + preco
				+ ", quantidade=" + quantidade + "]";
	}
	
	/**
	 * Retorna o subTotal (Preço * Qtde). Soma apenas produtos permitidos
	 * @return
	 */
	public Double getSubTotal() {
		return isNotAllowedItem() ? 0 : (this.preco * this.quantidade);
	}
	
	/**
	 * verifica se é um Item permitido
	 * @return
	 */
	public boolean isNotAllowedItem() {
		boolean a = NOT_ALLOWED_ITEMS.stream().anyMatch(this.sigla::contains);
		return a;
	}
	
}