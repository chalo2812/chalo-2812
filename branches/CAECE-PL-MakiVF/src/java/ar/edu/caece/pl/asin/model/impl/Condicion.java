package ar.edu.caece.pl.asin.model.impl;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TypeCheckVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Condicion extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS */
	private String envName;		//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	
	private int typeExp;			// El tipo de dato de <Expresion>
	private int typeExp1;			// El tipo de dato de <Expresion> (1)
	private int typeExp2;			// El tipo de dato de <Expresion> (2)
	
	private TreeNode expresion1;
	private TreeNode expresion2;
	int operador;	//Si es =, <=, >=, <, >, <> 
	
	
	/* INICIALIZACION */	
	public Condicion ( ITokenStream ts ) {
		super(ts, false);
	}
	public Condicion ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce una condicion del tipo expresion operador expresion, o even() y odd()
	 */
	public boolean reconocer () {

		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <CONDICION> -> 	ODD (<EXP>)		|
							EVEN(<EXP>) 	|
							<EXP>  = <EXP>	|
							<EXP> >= <EXP>	|
							<EXP> <= <EXP>	|
							<EXP>  < <EXP>	|
							<EXP>  > <EXP>	|
							<EXP> <> <EXP>
		 */
		
		if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_ODD) ) ) {
			// <CONDICION> -> ODD(<EXP>)
			
			this.setNroProduccion(PROD_PRIMERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			if( !this.reconocerToken(IToken.PALABRA_RESERVADA_ODD) ) {
				return false;
			}
			
			if( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
				return false;
			}
			
			// Reconocer Expresion
			Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
			expresion.setEnvName(this.envName);
			
			recognized &= expresion.reconocer();
			this.setValidated(expresion.validado());
			
			this.expresion1 = expresion.getTreeNode();
			
			// ASEM-TYPE_CHECK.
			this.typeExp = expresion.getTypeExpresion();
			this.accept(TypeCheckVisitor.getInstance());
			
			if( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
				return false;
			}
			
			this.accept(TreeVisitor.getInstance());
		
		} else if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_EVEN) ) ) {	
			// <CONDICION> -> EVEN(<EXP>)
			
			this.setNroProduccion(PROD_SEGUNDA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
					
			if( !this.reconocerToken(IToken.PALABRA_RESERVADA_EVEN) ) {
				return false;
			}
			
			if( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
				return false;
			}
			
			// Reconocer Expresion
			Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
			expresion.setEnvName(this.envName);
			
			recognized &= expresion.reconocer();
			this.setValidated(expresion.validado());
			
			this.expresion1 = expresion.getTreeNode();
			
			// ASEM-TYPE_CHECK.
			this.typeExp = expresion.getTypeExpresion();
			this.accept(TypeCheckVisitor.getInstance());
			
			if( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
				return false;
			}
			
			this.accept(TreeVisitor.getInstance());
		
		} else if ( Expresion.primeros(this.getTokenSiguiente()) ){	
			/* <CONDICION> -> 	<EXP>  = <EXP>	|
								<EXP> >= <EXP>	|
								<EXP> <= <EXP>	|
								<EXP>  < <EXP>	|
								<EXP>  > <EXP>	|
								<EXP> <> <EXP>
			 */
			
			this.setNroProduccion(PROD_TERCERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
			
			// Reconocer Expresion
			Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
			expresion.setEnvName(this.envName);
			
			recognized &= expresion.reconocer();
			this.setValidated(expresion.validado());
			
			this.expresion1 = expresion.getTreeNode();
			
			// Reconocer <C1> -->
			if (this.getTokenSiguiente().getType() == IToken.TYPE_OPER_IGUAL ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAYOR_IGUAL ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOR_IGUAL ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MAYOR ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_MENOR ||
				this.getTokenSiguiente().getType() == IToken.TYPE_OPER_DISTINTO ){

				this.reconocerToken(this.getTokenSiguiente().getType());
				
				this.operador = this.getTokenActual().getType();
				
			} else {
				//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
				em.error("Se esperaba operador de comparación", this.getTokenSiguiente());
				
				//Tratamiento de errores
				em.recover(this.getClass(), this.tokenStream);
				
				return false;
			}
			// <-- Reconocer <C1>
			
			// Reconocer <Expresion>
			Expresion expresion2 = new Expresion(this.tokenStream,this.debugMode);
			expresion2.setEnvName(this.envName);
			
			recognized &= expresion2.reconocer();
			this.setValidated(expresion2.validado());
			
			this.expresion2 = expresion2.getTreeNode();
			
			// ASEM-TYPE_CHECK.
			this.typeExp1 = expresion.getTypeExpresion();
			this.typeExp2 = expresion2.getTypeExpresion();
			this.accept(TypeCheckVisitor.getInstance());
			
			this.accept(TreeVisitor.getInstance());
			
		} else {
			
			//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
			em.error("Se esperaba " + IToken.PALABRA_RESERVADA_ODD +
					" o " + IToken.PALABRA_RESERVADA_EVEN +
					" o expresion", this.getTokenSiguiente());
			
			//Tratamiento de errores
			em.recover(this.getClass(), this.tokenStream);
			
			return false;
		}
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_ODD) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_EVEN) ) ||
				Expresion.primeros(token);
	}
	public int getTypeExp() {
		return typeExp;
	}
	public int getTypeExp1() {
		return typeExp1;
	}
	public int getTypeExp2() {
		return typeExp2;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public TreeNode getExpresion1() {
		return expresion1;
	}
	public TreeNode getExpresion2() {
		return expresion2;
	}
	public int getOperador() {
		return operador;
	}
	
}
