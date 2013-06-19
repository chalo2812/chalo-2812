package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.principal.Funcion;

public interface VisitableFuncNode {
	public Funcion acceptProcVisitor(FuncionNodeVisitor visitor);
}
