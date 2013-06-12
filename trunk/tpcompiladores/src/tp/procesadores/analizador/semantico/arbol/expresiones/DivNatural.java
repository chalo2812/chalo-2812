package tp.procesadores.analizador.semantico.arbol.expresiones;


public class DivNatural extends ClaseNodo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3179649586748641720L;

	public DivNatural(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}
	
	
}
