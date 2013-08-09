package tp.procesadores.analizador.semantico.arbol.principal;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.ProcedimientoNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Procedimiento extends ClaseNodo{

	private static final long serialVersionUID = 1L;
	private String nombreProcedimiento; 


	
	public String getNombreProcedimiento() {
		return nombreProcedimiento;
	}

	public void setNombreProcedimiento(String nombreProcedimiento) {
		this.nombreProcedimiento = nombreProcedimiento;
	}
	
	@Override
	public Procedimiento acceptProcVisitor(ProcedimientoNodeVisitor visitor) {
		return this;
	}
	
}
 