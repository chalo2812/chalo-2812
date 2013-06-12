package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;

/**
 * Token para guardar numeros Naturales
 */

public class Natural extends Token {
	private int lexema;
	public Natural(int fila, int columna, int natural) {
		super(fila, columna);
		lexema = natural; 
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
