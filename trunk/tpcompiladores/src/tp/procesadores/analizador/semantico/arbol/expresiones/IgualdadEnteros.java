package tp.procesadores.analizador.semantico.arbol.expresiones;


public class IgualdadEnteros extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	public IgualdadEnteros(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}


}
