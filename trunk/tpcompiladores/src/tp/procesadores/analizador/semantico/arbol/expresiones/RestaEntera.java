package tp.procesadores.analizador.semantico.arbol.expresiones;


public class RestaEntera extends ClaseNodo {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3252827611216132494L;

	public RestaEntera(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}


	
}
