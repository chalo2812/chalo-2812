package tp.procesadores.analizador.semantico.arbol.general;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class NodoCadena extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3164114736871392425L;
	private String lexema;
	
	public NodoCadena(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

}
