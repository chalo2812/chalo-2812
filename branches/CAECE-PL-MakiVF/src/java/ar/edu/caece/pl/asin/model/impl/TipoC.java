package ar.edu.caece.pl.asin.model.impl;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class TipoC extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS*/
	private int constType;  //Sintetizado. El tipo entero o natural reconocido
	private int constValue; //Sintetizado. El valor entero o natural reconocido
	
	/* INICIALIZACION */	
	public TipoC ( ITokenStream ts ) {
		super(ts, false);
	}
	public TipoC ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce el tipo y valor de las constantes
	 */
	public boolean reconocer() {
		
		recognized = true; 	//(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <TIPO_C> -> NATURAL = NUMERO_NAT |
         *             INTEGER = NUMERO     */
		
		if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_NATURAL) ) ) {

			// <TIPO_C> -> NATURAL = NUMERO_NAT <TIPO_C1>

			this.setNroProduccion(PROD_PRIMERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			// Reconocer NATURAL
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_NATURAL) ) {
				//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion de TipoC,
				//entonces no hay que seguir buscando sino salir
				return false;
			}
			
			this.constType = SymbolTable.NATURAL;
			
			// Reconocer TOKEN_OPER_IGUAL
			if ( !this.reconocerToken(IToken.TYPE_OPER_IGUAL) ) {
				return false;
			}
			
			// Reconocer NUMERO_NAT
			if ( !this.reconocerToken(IToken.TYPE_NUM_NATURAL) ) {
				return false;
			}
			
			String value = this.getTokenActual().getTokenText();
			value = value.replace("n", "");
			
			this.constValue = Integer.valueOf(value).intValue();
			
		} else if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_INTEGER) ) ) {
			
			// <TIPO_C> -> INTEGER = NUMERO 
			
			this.setNroProduccion(PROD_SEGUNDA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			// Reconocer INTEGER
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_INTEGER) ) {
				return false;
			}
			
			this.constType = SymbolTable.INTEGER;
			
			// Reconocer TOKEN_OPER_IGUAL
			if ( !this.reconocerToken(IToken.TYPE_OPER_IGUAL) ) {
				return false;
			}
			
			// Reconocer NUMERO_ENTERO
			if ( !this.reconocerToken(IToken.TYPE_NUM_ENTERO) ) {
				return false;
			}
			
			String value = this.getTokenActual().getTokenText();
			this.constValue = Integer.valueOf(value).intValue();
			
		} else {
			
			//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
			em.error("Se esperaba " + IToken.PALABRA_RESERVADA_NATURAL + " o " + IToken.PALABRA_RESERVADA_INTEGER,
				this.getTokenSiguiente());
			
			recognized = false;
			
			//Tratamiento de errores
			em.recover(this.getClass(), this.tokenStream);
		}
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_NATURAL) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_INTEGER));
	}

	public int getConstType() {
		return constType;
	}
	public int getConstValue() {
		return constValue;
	}
}
