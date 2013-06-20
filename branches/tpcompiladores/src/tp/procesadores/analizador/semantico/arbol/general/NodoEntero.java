package tp.procesadores.analizador.semantico.arbol.general;

import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitorInterface;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class NodoEntero extends ClaseNodo implements VisitableNode {

	private static final long serialVersionUID = 1L;
	private String lexema;
	
	public NodoEntero(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	@Override
	public String accept(NodeVisitorInterface visitor) {
		return visitor.visit(this);	
	}
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		codigo.setLabel(this.getLexema());
		codigo.setCodigo("");
		return codigo;
	}

}
