package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TypeCheckVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Termino extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS*/
	private int typeTermino;	// El tipo de <Termino>
	
	private String envName;		//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	
	private ArrayList<Integer> typeNTList = new ArrayList<Integer>();	// Los tipos de dato de producidos por <Termino>
	private ArrayList<Integer> typeTokenList = new ArrayList<Integer>();	// Los tipos de dato de los Tokens que reconocí.
	
	private int operador;	// Operador de la producción.
	private TreeNode nodoFactor = new TreeNode();		// Nodo Factor que es reconocido.
	private TreeNode nodoExpresion = new TreeNode();	// Nodo Expresion que es llamado por ToNatural y ToInteger.
	private String token;	// Token reconocido.
	
	/* INICIALIZACION */	
	public Termino ( ITokenStream ts ) {
		super(ts, false);
	}
	public Termino ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce un termino de una expresion
	 */
	public boolean reconocer () {
		
		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* 
		 * <TERMINO> -> <FACTOR>             			|
						<FACTOR> 	       * <TERMINO> 	|
						<FACTOR> 	      ** <TERMINO> 	|
						<FACTOR> 	       / <TERMINO> 	|
						<FACTOR>          // <TERMINO> 	|
						TONATURAL (<EXP>)    			|
						TONATURAL (<EXP>)  * <TERMINO> 	|
						TONATURAL (<EXP>) ** <TERMINO> 	|
						TONATURAL (<EXP>)  / <TERMINO> 	|
						TONATURAL (<EXP>) // <TERMINO> 	|
						TOINTEGER (<EXP>)             	|
						TOINTEGER (<EXP>)  * <TERMINO> 	|
						TOINTEGER (<EXP>)  / <TERMINO> 	|
						TOINTEGER (<EXP>) ** <TERMINO> 	|
						TOINTEGER (<EXP>) // <TERMINO>
		 */
		
		do{
			// Arreglo del loop. Hay que consumir los operadores.
			if( this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MULT_INT ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MULT_NAT ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_DIV_INT  ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_DIV_NAT) {
				
				// ASEM-TYPE_CHECK.
				typeTokenList.add(this.getTokenSiguiente().getType());
				
				// ASEM-TREE
				this.operador = this.getTokenSiguiente().getType();
				
				this.reconocerToken(this.getTokenSiguiente().getType());
			}
			
			if ( Factor.primeros(this.getTokenSiguiente()) ) {
				/* <TERMINO> -> <FACTOR>             			|
								<FACTOR> 	       * <TERMINO> 	|
								<FACTOR> 	      ** <TERMINO> 	|
								<FACTOR> 	       / <TERMINO> 	|
								<FACTOR>          // <TERMINO>
				 */
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				//Reconocer <Factor>
				Factor factor = new Factor(this.tokenStream, this.debugMode);
				factor.setEnvName(this.envName);
				
				recognized &= factor.reconocer();
				this.setValidated(factor.validado());
				
				// ASEM-TYPE_CHECK.
				typeNTList.add(factor.getTypeFactor());
				
				// ASEM-TREE
				this.nodoFactor = factor.getTreeNode();
				this.accept(TreeVisitor.getInstance());
			}
			else if (this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_TONATURAL) ) ||
					this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_TOINTEGER) )){
				/*
				 * <TERMINO> -> TONATURAL (<EXP>)    			|
								TONATURAL (<EXP>)  * <TERMINO> 	|
								TONATURAL (<EXP>) ** <TERMINO> 	|
								TONATURAL (<EXP>)  / <TERMINO> 	|
								TONATURAL (<EXP>) // <TERMINO> 	|
								TOINTEGER (<EXP>)             	|
								TOINTEGER (<EXP>)  * <TERMINO> 	|
								TOINTEGER (<EXP>)  / <TERMINO> 	|
								TOINTEGER (<EXP>) ** <TERMINO> 	|
								TOINTEGER (<EXP>) // <TERMINO> 
				 */
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				// TODO: LoggingVisitor.
				
				// ASEM-TREE. Para saber si es TONATURAL o TOINTEGER
				this.token = this.getTokenSiguiente().getTokenText();
				
				// ASEM-TYPE_CHECK.
				if (this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_TONATURAL)) ){
					this.typeNTList.add(SymbolTable.NATURAL);
				}
				else if (this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_TOINTEGER)) ){
					this.typeNTList.add(SymbolTable.INTEGER);
				}
				
				// Reconocer ToNatural o ToInteger				
				this.reconocerToken(this.getTokenSiguiente().getTokenText().toString());
				
				// Reconocer PARANTESIS_IZQ
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
					return false;
				}
				
				// Reconocer <Expresion>
				Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
				expresion.setEnvName(this.envName);
				
				recognized &= expresion.reconocer();
				this.setValidated(expresion.validado());
				
				// Reconocer PARANTESIS_DER
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
					return false;
				}

				// ASEM-TREE
				this.nodoExpresion = expresion.getTreeNode();
				this.accept(TreeVisitor.getInstance());
				
			}
			else{
				//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
				em.error("Se esperaba una produccion de <TERMINO>", this.getTokenSiguiente());
				
				//Tratamiento de errores
				em.recover(this.getClass(), this.tokenStream);
				return false;
			}
			
		} while (this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MULT_INT ||
				 this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MULT_NAT ||
				 this.getTokenSiguiente().getType() == IToken.TYPE_OPER_DIV_INT  ||
				 this.getTokenSiguiente().getType() == IToken.TYPE_OPER_DIV_NAT 	);
		
		// ASEM-TYPE_CHECK.
		this.accept(TypeCheckVisitor.getInstance());
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  Factor.primeros(token);
	}
	public int getTypeTermino() {
		return typeTermino;
	}
	public void setTypeTermino(int tt) {
		typeTermino = tt;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public ArrayList<Integer> getTypeNTList() {
		return typeNTList;
	}
	public ArrayList<Integer> getTypeTokenList() {
		return typeTokenList;
	}
	public int getOperador() {
		return operador;
	}
	public TreeNode getNodoExpresion() {
		return nodoExpresion;
	}
	public String getToken() {
		return token;
	}
	public TreeNode getNodoFactor() {
		return nodoFactor;
	}
}
