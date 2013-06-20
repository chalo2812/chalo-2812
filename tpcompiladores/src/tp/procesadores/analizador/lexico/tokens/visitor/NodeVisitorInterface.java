package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.general.NodoEntero;

public class NodeVisitorInterface extends NodeVisitor{

	public String visit(NodoEntero nodoEntero) {
		return null;//nodoEntero.nodos;
	}

	public String visit(Identificador identificador) {
		return null;
	}

}
