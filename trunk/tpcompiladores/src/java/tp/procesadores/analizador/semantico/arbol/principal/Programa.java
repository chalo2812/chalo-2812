package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.FuncionesPredefinidas;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Programa extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3664854674078244686L;

	public boolean esFuncion(int posicion){
		if (this.nodos.get(posicion).equals(Funcion.class))
			return true;
		return false;
	}

	public boolean esProcedimiento(int posicion){
		if (this.nodos.get(posicion).equals(Procedimiento.class))
			return true;
		return false;
	}
	
	public boolean esGlobales(int posicion){
		if (this.nodos.get(posicion).equals(Globales.class))
			return true;
		return false;
	}
	
}
