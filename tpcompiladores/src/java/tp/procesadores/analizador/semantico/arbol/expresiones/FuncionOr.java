package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.Label;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class FuncionOr extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	public FuncionOr(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}


	
}
