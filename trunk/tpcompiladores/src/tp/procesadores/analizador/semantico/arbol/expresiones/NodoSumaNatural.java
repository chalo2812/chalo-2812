package tp.procesadores.analizador.semantico.arbol.expresiones;


public class NodoSumaNatural extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4878780390922858799L;

	public NodoSumaNatural(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}

	
}
	