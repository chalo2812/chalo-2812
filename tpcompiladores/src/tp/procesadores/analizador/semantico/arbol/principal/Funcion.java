package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class Funcion extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1747953247467915175L;
	private String nombreFuncion;
	
	
	public String getNombreFuncion() {
		return nombreFuncion;
	}
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	} 
	


}
