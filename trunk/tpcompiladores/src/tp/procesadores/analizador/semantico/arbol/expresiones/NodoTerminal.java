package tp.procesadores.analizador.semantico.arbol.expresiones;

public class NodoTerminal extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5009175994531570318L;
	private String lexema;
	
	public NodoTerminal(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
}
