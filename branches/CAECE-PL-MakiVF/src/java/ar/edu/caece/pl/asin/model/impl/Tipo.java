package ar.edu.caece.pl.asin.model.impl;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Tipo extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS */
	private int type;	// El tipo de <Tipo>
	
	
	/* INICIALIZACION */	
	public Tipo ( ITokenStream ts ) {
		super(ts, false);
	}
	public Tipo ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}

	
	/**
	 * Devuelve true si reconoce integer o natural
	 */
	public boolean reconocer() {
		
		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <TIPO> -> INTEGER |
         *           NATURAL */
		if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_INTEGER) ) ) {
			// <TIPO> -> INTEGER
			this.setNroProduccion(PROD_PRIMERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
			
			//Reconocer integer
			this.reconocerToken(IToken.PALABRA_RESERVADA_INTEGER);
			this.type = SymbolTable.INTEGER;
			
		} else if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_NATURAL) ) ) {
			// <TIPO> -> NATURAL
			this.setNroProduccion(PROD_SEGUNDA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
			
			//Reconocer natural
			this.reconocerToken(IToken.PALABRA_RESERVADA_NATURAL);
			this.type = SymbolTable.NATURAL;
			
		} else {
			
			//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
			em.error("Se esperaba token "+ IToken.PALABRA_RESERVADA_INTEGER + " o " + IToken.PALABRA_RESERVADA_NATURAL,
				this.getTokenSiguiente());
			
			recognized = false;
			
			//Tratamiento de errores
			em.recover(this.getClass(), this.tokenStream);
		}
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_INTEGER) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_NATURAL) );
	}
	/**
	 * Devuelve si el VarMap es de INTEGERs o de NATURALs
	 * @return SymbolTable.INTEGER o NATURAL
	 */
	public int getType() {
		return type;
	}
}
