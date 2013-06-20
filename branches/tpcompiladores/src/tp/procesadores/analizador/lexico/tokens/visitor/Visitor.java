package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.lexico.tokens.*;
import tp.procesadores.analizador.lexico.tokens.Error;

/**
 * La clase que implemente esta interface podra visitar a cualquier Token 
 */

public interface Visitor {
	
	public String visit(Token token);
	public String visit(Palabra palabra);
	public String visit(PalabraReservada palabra);
	public String visit(Operador operador);
	public String visit(Eof eof);
	public String visit(Error error);
	public String visit(Natural natural);
	public String visit(Entero entero);
	public String visit(Cadena cadena);
}
