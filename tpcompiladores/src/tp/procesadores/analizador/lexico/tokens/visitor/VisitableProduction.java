package tp.procesadores.analizador.lexico.tokens.visitor;

public interface VisitableProduction {

	public Object accept(ProductionVisitor visitor);
	
}
