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
import tp.procesadores.analizador.semantico.arbol.principal.Globales;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class ClaseNodo implements InterfazNodo, Serializable, VisitableNode, 
		VisitableProcedimientoNode,	VisitableFuncionNode, VisitableTablaDeSimbolos{
	
	private static final long serialVersionUID = 7322187738235400509L;
	public List<InterfazNodo> nodos = new ArrayList<InterfazNodo>();
	public String lexema;
	public String contexto; 

	public void add(InterfazNodo nodo) {
		nodos.add(nodo);
	}

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

	public Funcion acceptFuncVisitor(FuncionNodeVisitor visitor) {
		return new Funcion();
	}

	public TablaDeSimbolos acceptTSVisitor(TablaSimbolosVisitor visitor) {
		return null;
	}

	public String accept(NodeVisitor identVisitor) {
		return identVisitor.getLexema();
	}
	
	public String getContexto(){
		return this.contexto;
	}

	public String getLexema(){
		return this.lexema;
	}
	
	public void setContexto(String mensaje){
		this.contexto = mensaje;
	}

	public boolean esFuncion(InterfazNodo interfazNodo){
		if (interfazNodo.getClass().equals(Funcion.class))
			return true;
		return false;
	}

	public boolean esProcedimiento(InterfazNodo interfazNodo){
		if (interfazNodo.getClass().equals(Procedimiento.class))
			return true;
		return false;
	}
	
	public boolean esGlobales(InterfazNodo interfazNodo){
		if (interfazNodo.getClass().equals(Globales.class))
			return true;
		return false;
	}

	public String nombreDelProceso(List<InterfazNodo> posicion, int pos, int posMetodo) {
		TablaDeSimbolos objeto = (TablaDeSimbolos) posicion.get(0);
		String mensaje = objeto.padre.metodos.get(posMetodo).getNombre();
		return mensaje;
	}	
	
}