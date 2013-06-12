package tp.procesadores.analizador.semantico.arbol.expresiones;


public class DistintoEnteros extends ClaseNodo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9046572418979142818L;

	public DistintoEnteros(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}
	


}
