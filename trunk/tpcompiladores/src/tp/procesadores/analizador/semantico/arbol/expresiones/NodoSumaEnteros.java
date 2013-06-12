package tp.procesadores.analizador.semantico.arbol.expresiones;


public class NodoSumaEnteros extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3684745677426530645L;

	public NodoSumaEnteros(ClaseNodo nodo1, ClaseNodo nodo2) {
		this.add(nodo1);
		this.add(nodo2);
	}


}
