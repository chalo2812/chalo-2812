package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.treeelements.Cadena;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class ShowElem extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS*/
	private String envName;		//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	private List<TreeNode> elementos = new ArrayList<TreeNode>();
	
	/* INICIALIZACION */	
	public ShowElem ( ITokenStream ts ) {
		super(ts, false);
	}
	public ShowElem ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce los parametros con los que se llama a show y showln
	 */
	public boolean reconocer () {

		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <SHOW_ELEM> -> CADENA  |
						  <EXP>   |
						  CADENA , <SHOW_ELEM> |
						  <EXP>  , <SHOW_ELEM>
         */
		
		do {
		 	// Arreglo del loop. Hay que consumir la coma.
			if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {
				this.reconocerToken(IToken.TYPE_COMA);
			}
			
			if ( this.getTokenSiguiente().equals( new Token (IToken.TYPE_CADENA) ) ) {
				// <SHOW_ELEM> -> CADENA
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				if( !this.reconocerToken(IToken.TYPE_CADENA) ) {
					return false;
				}
				
				this.agregarElemento(this.getTokenActual().getTokenText());
			
			} else if ( Expresion.primeros(this.getTokenSiguiente()) ){	
				
				// <SHOW_ELEM> -> <EXP>
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer <EXP>
				Expresion expresion = new Expresion(this.tokenStream, this.debugMode);
				expresion.setEnvName(this.envName);
				
				recognized &= expresion.reconocer();
				this.setValidated(expresion.validado());
				
				this.agregarElemento(expresion.getTreeNode());
			
			} else {
				
				//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
				em.error("Se esperaba una produccion de <SHOW_ELEM>", this.getTokenSiguiente());
				
				//Tratamiento de errores
				em.recover(this.getClass(), this.tokenStream);
				
				return false;
			}
			
		} while(this.getTokenSiguiente().getType() == IToken.TYPE_COMA );
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.TYPE_CADENA) ) ||
				Expresion.primeros(token);
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public List<TreeNode> getElementos() {
		return elementos;
	}
	
	private void agregarElemento(String cadena) {
		this.elementos.add(new TreeNode(new Cadena(cadena)));
	}
	private void agregarElemento(TreeNode expresion) {
		this.elementos.add(expresion);
	}
}
