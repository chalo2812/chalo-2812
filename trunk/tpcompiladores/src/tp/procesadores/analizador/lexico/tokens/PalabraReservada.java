package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;

/**
 * Token para guardar Palabras Reservadas del lenguaje  
 */
public class PalabraReservada  extends Token {
	private String lexema; 
	public PalabraReservada(int fila, int columna, String palabra) {
		super(fila, columna); 
		lexema = palabra; 
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
