package tp.procesadores.analizador.semantico.arbol.expresiones;

public class DivEntera extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5995496000587620032L;

	public DivEntera(ClaseNodo nodo1, ClaseNodo nodo2) {
		this.add(nodo1);
		this.add(nodo2);
	}

}
