package br.com.sysmi.labproject.util;

public class JsonSamples {
	
	private static final String COMPRA_OK = "{\n"+
			"\"idCompra\":123,\n"+
			"\"nomeComprador\":\"Marcelo Lima\",\n"+
			"\"identidade\":12345,\n"+
			"\"items\":["+  
			      "{\n"+  
			         "\"itemId\":123,\n"+
			         "\"sigla\":\"BCK\",\n"+
			         "\"descricao\":\"Bicicleta\",\n"+
			         "\"preco\":125.00,\n"+
			         "\"quantidade\":1\n"+
			      "},\n"+
			      "{\n"+  
			      "\"itemId\":222,\n"+
			      "\"sigla\":\"TRD\",\n"+
			      "\"descricao\":\"Torradeira\",\n"+
			      "\"preco\":15.00,\n"+
			      "\"quantidade\":3\n"+
			      "}\n"+
			   "]\n"+
			"}";
	
	private static final String COMPRA_SEM_ITEMS = "{\n"+
			"\"idCompra\":123,\n"+
			"\"nomeComprador\":\"Marcelo Lima\",\n"+
			"\"identidade\":12345,\n"+
			"\"items\":["+
			   "]\n"+
			"}";
	
	private static final String COMPRA_INVALIDA = "lma1dfkjh4734yvsdfiusy9fh39";
	
	private static final String COMPRA_VAZIA = " ";
	
	
	/**
	 * M�todos 'get' necess�rios para a cobertura desta classe n�o ficar com 0%.
	 */
	public static String getCOMPRA_OK() {
		return COMPRA_OK;
	}
	
	public static String getCOMPRA_SEM_ITEMS() {
		return COMPRA_SEM_ITEMS;
	}
	
	public static String getCOMPRA_INVALIDA() {
		return COMPRA_INVALIDA;
	}
	
	public static String getCOMPRA_VAZIA() {
		return COMPRA_VAZIA;
	}
}