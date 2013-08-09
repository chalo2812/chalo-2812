package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar Operadores, aritmeticos y del leguaje.  
 */
public class Operador extends Token {
	public String lexema;
	public String contexto;

	public Operador(int fila, int columna, String operador) {
		super(fila, columna);
		lexema = operador;
	}
	public String getLexema() {
		return lexema;
	}
	
	public String getContexto() {
		return contexto;
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
}
