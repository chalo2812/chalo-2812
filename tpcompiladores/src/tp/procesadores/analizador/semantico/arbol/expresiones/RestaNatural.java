package tp.procesadores.analizador.semantico.arbol.expresiones;


public class RestaNatural extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4217476845354683541L;

	public RestaNatural(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}

}
