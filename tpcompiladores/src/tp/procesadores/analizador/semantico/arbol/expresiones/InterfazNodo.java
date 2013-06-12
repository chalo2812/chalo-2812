package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.analizador.lexico.tokens.visitor.ProcedimientoNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

	
/**
 * Interfaz del Nodo para el composite del Arbol 
 *
 */
public interface InterfazNodo extends VisitableNode {
	
	public void add(InterfazNodo nodo);

	public void remove(InterfazNodo nodo);

	public TablaDeSimbolos acceptTSVisitor(
			TablaSimbolosVisitor tablaSimbolosVisitor);

	public Procedimiento acceptProcVisitor(ProcedimientoNodeVisitor visitor);

	public Codigo generarCodigo(Codigo codigo, TempManager tempManager,
			LabelManager labelManager);

}
 