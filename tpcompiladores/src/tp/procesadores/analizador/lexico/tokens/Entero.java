package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;

/**
 * Token para guardar numeros enteros 
 */

public class Entero extends Token {
	private int lexema; 
	public Entero(int fila, int columna, int lex) {
		super(fila, columna);
		lexema = lex; 
	}
	public int getLexema() {
		return lexema;
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
	
	
}
