package tp.procesadores.analizador.semantico.arbol.expresiones;


public class FuncionOr extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7581128149626461488L;

	public FuncionOr(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}


	
}
