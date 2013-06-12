package tp.procesadores.analizador.semantico.arbol.general;

import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class NodoNatural extends ClaseNodo implements VisitableNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7731417876561875514L;
	private String lexema;
	
	public NodoNatural(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	
}
