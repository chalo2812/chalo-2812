package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.treeelements.Constante;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Header extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS */
	private List<SimboloGenerico> declaraciones = new ArrayList<SimboloGenerico>(); //Sintetizado
	//private String envName;		//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	
	/* INICIALIZACION */	
	public Header ( ITokenStream ts ) {
		super(ts, false);
	}
	public Header ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/**
	 * Devuelve true si reconoce un bloque de constantes y variables
	 */
	public boolean reconocer () {
		
		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <HEADER> -> CONST IDENTIFICADOR : <TIPO_C>; <HEADER> | 
           			   VAR <VAR>; <HEADER> |
           			   LAMBDA				*/
		while ( Header.primeros(this.getTokenSiguiente()) ) {
			
			if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_CONST) ) ) {
				
				//<HEADER> -> CONST IDENTIFICADOR : <TIPO_C>; <HEADER>
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer Palabra Reservada "const"
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_CONST) ) {
					//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion del Header,
					//entonces no hay que seguir buscando sino salir
					return false;
				}
				
				do {	//Cambio de Recursion por Iteracion
					
					if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {	//Arreglo del loop.
						this.reconocerToken(IToken.TYPE_COMA);						//Hay que consumir la coma.
					}
					
					// Reconocer Identificador
					if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
						return false;
					}
					
					String constName = this.getTokenActual().getTokenText();
					
					//Reconocer Dos Puntos
					if ( !this.reconocerToken(IToken.TYPE_DOS_PUNTOS) ) {
						return false;
					}
					
					TipoC tipoC = new TipoC(this.tokenStream, this.debugMode);
					
					recognized &= tipoC.reconocer();
					this.setValidated(tipoC.validado());
					
					this.crearConstante(constName, tipoC.getConstType(), tipoC.getConstValue());
					
				} while (this.getTokenSiguiente().equals(new Token(IToken.TYPE_COMA)));
				
				//Reconocer Punto y Coma
				if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
					return false;
				}
				
			} else if (this.getTokenSiguiente().equals(new Token(IToken.PALABRA_RESERVADA_VAR) ) ) {
				
				// <HEADER> -> VAR <VAR>; <HEADER>
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				//Reconocer Palabra reservada VAR
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_VAR) ) {
					return false;
				}
				
				// Reconocer <VAR>
				do {
					Var var = new Var(this.tokenStream, this.debugMode);
					recognized &= var.reconocer();
					this.setValidated(var.validado());
					
					this.declaraciones.addAll(var.getVariables());
					
				} while ( Var.primeros(this.getTokenSiguiente() ) );
			}
		}
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_CONST) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_VAR) );
	}
	private void crearConstante(String name, int type, int value) {
		this.declaraciones.add(new Constante(name, type, value));
	}
	public List<SimboloGenerico> getDeclaraciones() {
		return declaraciones;
	}
//	public String getEnvName() {
//		return envName;
//	}
//	public void setEnvName(String envName) {
//		this.envName = envName;
//	}
}
