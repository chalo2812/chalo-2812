package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.lexico.tokens.visitor.ProcedimientoNodeVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class Procedimiento extends ClaseNodo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1983279449305825335L;
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
 