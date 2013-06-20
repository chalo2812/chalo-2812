package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar la marca de fin de archivo EOF 
 */
public class Eof extends Token {
	
	private String lexema; 
	public Eof(int fila, int columna) {
		super(fila, columna);
		lexema="EOF";
	}
	
	public String getLexema() {
		return lexema;
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
}
