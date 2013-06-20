package tp.procesadores.analizador.semantico.arbol.general;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitorInterface;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Identificador extends ClaseNodo implements VisitableNode {

	private static final long serialVersionUID = 1L;
	private String lexema;
	private String contexto; 
	
	public Identificador(String lexema){
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
	

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
}
