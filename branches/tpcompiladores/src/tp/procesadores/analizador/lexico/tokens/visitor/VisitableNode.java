package tp.procesadores.analizador.lexico.tokens.visitor;


public interface VisitableNode {

	public String accept(NodeVisitorInterface visitor);
	
	
}
