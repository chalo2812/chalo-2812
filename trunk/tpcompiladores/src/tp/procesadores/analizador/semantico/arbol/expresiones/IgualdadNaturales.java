package tp.procesadores.analizador.semantico.arbol.expresiones;


public class IgualdadNaturales extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9170240050006742338L;

	public IgualdadNaturales ( ClaseNodo nodo1, ClaseNodo nodo2 ){
		this.add(nodo1);
		this.add(nodo2);
	}
	


}
