package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class NodoSumaNatural extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	public NodoSumaNatural(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}

	
}
	