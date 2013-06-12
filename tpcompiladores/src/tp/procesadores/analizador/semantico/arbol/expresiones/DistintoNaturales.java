package tp.procesadores.analizador.semantico.arbol.expresiones;


public class DistintoNaturales extends ClaseNodo {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5215835063388472148L;

	public DistintoNaturales(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}

	
}
