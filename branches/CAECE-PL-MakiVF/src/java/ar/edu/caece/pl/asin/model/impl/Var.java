package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.treeelements.Variable;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Var extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {

	/* ATRIBUTOS */
	List<SimboloGenerico> variables = new ArrayList<SimboloGenerico>();
	
	/* INICIALIZACION */	
	public Var ( ITokenStream ts ) {
		super(ts, false);
	}
	public Var ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/**
	 * Devuelve true si reconoce una variable
	 */
	public boolean reconocer () {
		
		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		Map<String,Integer> temp = new LinkedHashMap<String, Integer>();
			
		do {	/* <VAR> --> IDENTIFICADOR <VAR_1> */
			
			if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {	//Arreglo del loop.
				this.reconocerToken(IToken.TYPE_COMA);						//Hay que consumir la coma.
			}
			
			this.setNroProduccion(PROD_ENCABEZADO);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			//Reconocer Identificador
			if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
				//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion de Var,
				//entonces no hay que seguir buscando sino salir
				return false;
			}
			
			String varName = this.getTokenActual().getTokenText();
			int length = 0;
			
			if ( this.getTokenSiguiente().getType() == IToken.TYPE_CORCHETE_IZQ ){
				
				this.reconocerToken(IToken.TYPE_CORCHETE_IZQ);
				
				if ( this.getTokenSiguiente().getType() == IToken.TYPE_NUM_ENTERO ) {
					// <VAR> -> IDENTIFICADOR [ NUMERO ]
					
					this.setNroProduccion(PROD_TERCERA);
					this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
					
					//Reconocer Numero entero    // <VAR_2> -> NUMERO ] <VAR_3>
					this.reconocerToken(IToken.TYPE_NUM_ENTERO);
					length = Integer.valueOf(this.getTokenActual().getTokenText());
					
				} else if ( this.getTokenSiguiente().getType() == IToken.TYPE_NUM_NATURAL ) {
					// <VAR> -> IDENTIFICADOR [ NUMERO_NAT ]
					
					this.setNroProduccion(PROD_CUARTA);
					this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
					
					//Reconocer Numero natural     // <VAR_2> -> NUMERO_NAT ] <VAR_3>
					this.reconocerToken(IToken.TYPE_NUM_NATURAL);
					length = Integer.valueOf(this.getTokenActual().getTokenText().replace("n", ""));
					
				} else {
					
					em.error("Se esperaba token "+ this.dic.getTokenDescription(IToken.TYPE_NUM_ENTERO), this.getTokenSiguiente());
					em.recover(this.getClass(), this.tokenStream);
					return false;
				}
				
				if ( !this.reconocerToken(IToken.TYPE_CORCHETE_DER) ) {
					return false;
				}
			}
			else{
				// <VAR> → IDENTIFICADOR , <VAR>
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
				
			}
			
			temp.put(varName, length);
			
		} while ( this.getTokenSiguiente().getType() == IToken.TYPE_COMA );
		
		
		//Reconocer dos puntos
		if ( !this.reconocerToken(IToken.TYPE_DOS_PUNTOS) ) {
			return false;
		}
		else{
			// <VAR> → IDENTIFICADOR : <TIPO>;
			
			this.setNroProduccion(PROD_SEGUNDA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			
			//Reconocer <Tipo>
			Tipo tipo = new Tipo(this.tokenStream, this.debugMode);
			tipo.setTreeNode(this.getTreeNode());
			
			recognized &= tipo.reconocer();
			this.setValidated(tipo.validado());
			
			int type = tipo.getType();
			
			this.construirListaVariables(temp,type);
			
			//Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
		}
		
		
		//LISTO la definicion de variable
		//Finalmente terminamos de reconocer esta derivacion de Header
		return recognized;
	}
	
	private void construirListaVariables(Map<String,Integer> temp, int type) {
		for(Entry<String, Integer> entry : temp.entrySet()) {
			SimboloGenerico sg;
			if (entry.getValue() == 0) {
				sg = new Variable(entry.getKey(), type);
			} else {
				sg = new Arreglo(entry.getKey(), type, entry.getValue());
			}
			this.variables.add(sg);
		}
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.TYPE_IDENTIFICADOR) );
	}
	public List<SimboloGenerico> getVariables() {
		return variables;
	}
}
