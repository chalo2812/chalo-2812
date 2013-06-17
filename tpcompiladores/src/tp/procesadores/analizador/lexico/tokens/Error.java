package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
/**
 *Token para guardar errores con sus respectivos codigos  
 */
public class Error extends Token {

	private final int codError;
	private String lexema; 
	
	public Error(int fila, int columna, String lex, int codigo) {
		super(fila, columna);
		codError=codigo;
		lexema = lex;
	}

	public int getCodError() {
		return codError;
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
