package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public interface ProductionVisitor {

	public Object visit(ProduccionC produccionC);
}
