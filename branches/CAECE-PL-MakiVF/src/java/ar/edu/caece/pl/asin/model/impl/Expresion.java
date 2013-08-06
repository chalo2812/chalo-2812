package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TypeCheckVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Expresion extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS */
	private int typeExpresion;		// El tipo de dato de <Expresion> que produce
	
	private String envName;			//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	
	private ArrayList<Integer> typeTerminoList = new ArrayList<Integer>();	// Los tipos de dato de <EXP> que produjo.
	private ArrayList<Integer> typeTokenList = new ArrayList<Integer>();	// Los tipos de dato de los Tokens que reconocí.
	
	private int operador;	// Operador de la producción.
	private TreeNode nodoTermino = new TreeNode();		// Nodo Factor que es reconocido.
	
	/* INICIALIZACION */	
	public Expresion ( ITokenStream ts ) {
		super(ts, false);
	}
	public Expresion ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce una expresion
	 */
	public boolean reconocer () {

		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <EXP> ->	<TERMINO>  ++ <EXP>	|
				  	<TERMINO>   + <EXP>	|
				  	<TERMINO>  -- <EXP> |
				  	<TERMINO>   - <EXP>	|
			  		<TERMINO>
		 */
		
		do{
			// Arreglo del loop. Hay que consumir los operadores.
			if( this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_INT    ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_NAT    ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_INT  ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_NAT) {
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
				
				// ASEM-TYPE_CHECK.
//				if (this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_INT || this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_INT){
//					typeTokenList.add(SymbolTable.INTEGER);
//				}else if(this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_NAT || this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_NAT){
//					typeTokenList.add(SymbolTable.NATURAL);
//				}
				typeTokenList.add(this.getTokenSiguiente().getType());
				
				// ASEM-TREE
				this.operador = this.getTokenSiguiente().getType();
				
				this.reconocerToken(this.getTokenSiguiente().getType());
			}
			
			// Reconocer Termino
			Termino termino = new Termino(this.tokenStream,this.debugMode);
			termino.setEnvName(this.envName);
			
			recognized &= termino.reconocer();
			this.setValidated(termino.validado());
			
			// ASEM-TYPE_CHECK.
			typeTerminoList.add(termino.getTypeTermino());
			
			// ASEM-TREE
			this.nodoTermino = termino.getTreeNode();
			this.accept(TreeVisitor.getInstance());
			
		}while( this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_INT    ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAS_NAT    ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_INT  ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOS_NAT  );
		
		// ASEM-TYPE_CHECK.
		this.accept(TypeCheckVisitor.getInstance());
		
		return recognized;
	}
	

	public static boolean primeros(IToken token) {
		// Primeros: TONATURAL, TOINTEGER, ( , IDENTIFICADOR, NUMERO, NUMERO_NAT
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_TONATURAL) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_TOINTEGER) ) ||
				Termino.primeros(token);
	}
	public int getTypeExpresion() {
		return typeExpresion;
	}
	public void setTypeExpresion(int typeExpresion) {
		this.typeExpresion = typeExpresion;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public ArrayList<Integer> getTypeTerminoList() {
		return typeTerminoList;
	}
	public ArrayList<Integer> getTypeTokenList() {
		return typeTokenList;
	}
	public int getOperador() {
		return operador;
	}
	public TreeNode getNodoTermino() {
		return nodoTermino;
	}
}
