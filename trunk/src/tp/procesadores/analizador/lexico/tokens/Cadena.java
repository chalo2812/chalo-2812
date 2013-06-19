package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar Cadenas de caracteres encerradas entre comilla simple  
 *
 */
public class Cadena extends Token {
	private String lexema;
	public Cadena(int fila, int columna, String cadena) {
		super(fila, columna);
		lexema = cadena; 
	}
	public String getLexema() {
		return lexema;
	}
	
	@Override 
	public String accept(TokensVisitor visitor) {
			return visitor.visit(this);	
	}
	


}
