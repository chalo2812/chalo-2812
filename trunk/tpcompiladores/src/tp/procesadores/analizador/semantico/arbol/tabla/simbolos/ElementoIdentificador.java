package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class ElementoIdentificador extends ClaseNodo {
	
	private static final long serialVersionUID = 1L;
	private String tipo;
	private String valor;
	private String lexema;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	} 
	
	
	
}
