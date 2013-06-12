package tp.procesadores.analizador.semantico.arbol.expresiones;


public class ProductoEntero extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6073781628533333903L;

	public ProductoEntero(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}
	

}
