package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class BloqueVarParam extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	/* ATRIBUTOS */
	private List<Parametro> parametros = new ArrayList<Parametro>();
	
	/* INICIALIZACION */	
	public BloqueVarParam ( ITokenStream ts ) {
		super(ts, false);
	}
	public BloqueVarParam ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}

	/** 
	 * Devuelve true si reconoce los parametros de llamada a un procedimiento o funcion
	 */
	public boolean reconocer () {
		
		recognized = true;	//(asumimos correctitud hasta demostrar lo contrario)
		
		// Itero las veces que <Bloque_var_params1> no sea LAMBA, es decir, me vuelva a invocar.
		do {
			int paramType = -1;	//INT o NAT
			int paramMode = -1;	//BYREF o BYVAL
			String paramName = null;
			
			//Arreglo del loop. Hay que consumir la coma.
			if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {	
				this.reconocerToken(IToken.TYPE_COMA);
			}
			
			this.setNroProduccion(PROD_ENCABEZADO);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			/* <BLOQUE_VAR_PARAM> -> 	BYREF IDENTIFICADOR : <TIPO> 						|
				                    	BYVAL IDENTIFICADOR : <TIPO> 						|
				                    	IDENTIFICADOR       : <TIPO> 						|
										BYREF IDENTIFICADOR : <TIPO> , <BLOQUE_VAR_PARAM> 	|
				                    	BYVAL IDENTIFICADOR : <TIPO> , <BLOQUE_VAR_PARAM> 	|
				                    	IDENTIFICADOR       : <TIPO> , <BLOQUE_VAR_PARAM>
	         */
			
			//Primero reconozco Byref, Byval o si viene por default y agrego info de debug 
			if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_BYREF) ) ) {
				
				// <BLOQUE_VAR_PARAM> -> BYREF IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_BYREF) ) {
					//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion del BloqueVarParam,
					//entonces no hay que seguir buscando sino salir
					return false;
				}
				
				paramMode = Parametro.BYREF;
				
			} else if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_BYVAL) ) ) {
				
				// <BLOQUE_VAR_PARAM> -> BYVAL IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_BYVAL) ) {
					return false;
				}
				
				paramMode = Parametro.BYVAL;
				
			} else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_IDENTIFICADOR) ) ) {
				
				// <BLOQUE_VAR_PARAM> -> IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>
				
				this.setNroProduccion(PROD_TERCERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				paramMode = Parametro.BYVAL;
				
			} else {
				
				//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
				em.error("Se esperaba " + IToken.PALABRA_RESERVADA_BYREF +
						 " o " + IToken.PALABRA_RESERVADA_BYVAL +
						 " o " + this.dic.getTokenDescription(IToken.TYPE_IDENTIFICADOR),
						this.getTokenSiguiente());
				
				//Tratamiento de errores
				em.recover(this.getClass(), this.tokenStream);
				
				return false;
			}
			
			//Luego el resto comun a las tres producciones IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>
			
			if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
				return false;
			}
			
			paramName = this.getTokenActual().getTokenText();
			
			if ( !this.reconocerToken(IToken.TYPE_DOS_PUNTOS) ) {
				return false;
			}
	
			// Reconocer <Tipo>
			Tipo tipo = new Tipo(this.tokenStream, this.debugMode);
			
			recognized &= tipo.reconocer();
			this.setValidated(tipo.validado());
			
			paramType = tipo.getType();
			
			this.agregarParametro(paramName, paramType, paramMode);
			
		} while(this.getTokenSiguiente().equals(new Token(IToken.TYPE_COMA)));
		
		return recognized;
	}
	
	private void agregarParametro( String name, int tipo, int modo) {
		Parametro p = new Parametro(name, tipo, modo);
		this.parametros.add(p);
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.TYPE_IDENTIFICADOR) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_BYREF) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_BYVAL) );
	}
	public List<Parametro> getParametros() {
		return parametros;
	}
}
