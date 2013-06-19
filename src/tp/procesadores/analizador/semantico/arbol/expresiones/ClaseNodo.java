package tp.procesadores.analizador.semantico.arbol.expresiones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.FuncionNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitorInterface;
import tp.procesadores.analizador.lexico.tokens.visitor.ProcedimientoNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableFuncionNode;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableProcedimientoNode;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableTablaDeSimbolos;
import tp.procesadores.analizador.semantico.arbol.principal.Funcion;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class ClaseNodo implements 
		InterfazNodo, Serializable, VisitableNode, VisitableProcedimientoNode, 
		VisitableFuncionNode, VisitableTablaDeSimbolos{
	
	private static final long serialVersionUID = 1L;
	
	public List<InterfazNodo> nodos = new ArrayList<InterfazNodo>();
	 
	@Override
	public void add(InterfazNodo nodo) {
		nodos.add(nodo);
	}

	@Override
	public void remove(InterfazNodo nodo) {
		nodos.remove(nodo);
	}
	
	public Object clone()
	{
	    try {
	      return super.clone();
	    } catch (CloneNotSupportedException e) {
	      System.out.println("Cloning not allowed.");
	      return this;
	    }
	}
	
	@Override
	public String accept(NodeVisitorInterface visitor) {
		return null;
	}
	
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager) 
	{
		Codigo resultado = new Codigo();
		resultado.setCodigo(codigo.getCodigo());
		
		return resultado;
	}

	
	public Procedimiento acceptProcVisitor(ProcedimientoNodeVisitor visitor) {
		return null;
	}

	@Override
	public Funcion acceptFuncVisitor(FuncionNodeVisitor visitor) {
		return new Funcion();
	}

	@Override
	public TablaDeSimbolos acceptTSVisitor(TablaSimbolosVisitor visitor) {
		return null;
	}

	public String accept(NodeVisitor identVisitor) {
		return identVisitor.getLexema();
	}

}
