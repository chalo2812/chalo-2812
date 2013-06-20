package tp.procesadores.analizador.semantico.arbol.principal;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.FuncionNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Funcion extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	private String nombreFuncion;
	
	
	public String getNombreFuncion() {
		return nombreFuncion;
	}
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	} 
	


}
