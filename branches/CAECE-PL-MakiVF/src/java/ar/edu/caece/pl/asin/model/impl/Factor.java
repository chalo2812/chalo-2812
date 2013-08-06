package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TypeCheckVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class Factor extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS*/
	private int typeFactor;													// El tipo de dato de <Factor>
	
	private String envName;													// Nombre del ambiente donde se estan creando las constantes
	
	private int typeExpresion;												// El tipo de dato de <Expresion>
	private ArrayList<Integer> typeFactorList = new ArrayList<Integer>();	// Lista de tipos de datos de <Factor1>
	private List<TreeNode> parametros = new ArrayList<TreeNode>();			// Lista de parametros de una llamada a funcion ó procedimiento.
	private String identificador;											// Nombre del identificador
	private int numValue;													// Valor del Nro Entero o Natural
	
	/* INICIALIZACION */	
	public Factor ( ITokenStream ts ) {
		super(ts, false);
	}
	public Factor ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce un factor
	 */
	public boolean reconocer () {

		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <FACTOR> ->	( <EXP> )   				| 
           				NUMERO     					|
           				NUMERO_NAT 					|
           				IDENTIFICADOR ( <FACTOR1> )	|
			       		IDENTIFICADOR [ <EXP> ]		|
			       		IDENTIFICADOR
		   
		   <FACTOR1> -> <EXP> , <FACTOR1>	|
      					<EXP> 				|
             			LAMBDA
         */

		if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_PARENTESIS_IZQ)) ) {
			
			// <FACTOR> -> ( <EXP> )
			
			this.setNroProduccion(PROD_PRIMERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
			
			// Reconocer Parentesis Izq
			if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
				//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion,
				//entonces no hay que seguir buscando sino salir
				return false;
			}
			
			// Reconocer Expresion
			Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
			expresion.setEnvName(this.envName);
			
			recognized &= expresion.reconocer();
			this.setValidated(expresion.validado());
			
			//Factor no es un nodo en si, sino que será el nodo de la expresion que éste contiene (en este caso).
			this.setTreeNode(expresion.getTreeNode());		//Es mas corto hacerlo asi que ponerlo como atributo y dentro del visitor pisar los nodos...
			
			// Reconocer Parentesis Der
			if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
				return false;
			}
			
			// ASEM-TYPE_CHECK. Obtengo el tipo de dato
			this.typeFactor = expresion.getTypeExpresion();
			
		} else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_NUM_ENTERO))){
			
			// <FACTOR> -> NUMERO
			
			this.setNroProduccion(PROD_SEGUNDA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
			
			// Reconocer Natural o Integer
			if ( !this.reconocerToken(IToken.TYPE_NUM_ENTERO) ) {
				return false;
			}
			
			this.numValue = Integer.parseInt(this.getTokenActual().getTokenText());
			
			// ASEM-TYPE_CHECK.
			this.accept(TypeCheckVisitor.getInstance());
			
			//ASEM-TREE			
			this.accept(TreeVisitor.getInstance());
		}
		else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_NUM_NATURAL))) {
			
			// <FACTOR> -> NUMERO_NAT
			
			this.setNroProduccion(PROD_TERCERA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
			
			// Reconocer Natural o Integer
			if ( !this.reconocerToken(IToken.TYPE_NUM_NATURAL) ) {
				return false;
			}
			
			this.numValue = Integer.parseInt(this.getTokenActual().getTokenText().replace("n", ""));
			
			// ASEM-TYPE_CHECK.
			this.accept(TypeCheckVisitor.getInstance());
			
			// ASEM-TREE
			this.accept(TreeVisitor.getInstance());
			
		} else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_IDENTIFICADOR)) ) {
			
			/* <FACTOR> ->	IDENTIFICADOR ( <FACTOR1> )	|
			          		IDENTIFICADOR [ <EXP> ]		|
			          		IDENTIFICADOR
			   
			   <FACTOR1> -> <EXP> , <FACTOR1>	|
	      					<EXP> 				|
	             			LAMBDA
			 */
			
			// Reconocer Identificador
			if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
				return false;
			}
			
			// ASEM-TYPE_CHECK.
			this.identificador = this.getTokenActual().getTokenText();
			
			if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_PARENTESIS_IZQ)) ) {
				
				// <FACTOR> ->	IDENTIFICADOR ( <FACTOR1> )
				
				this.setNroProduccion(PROD_CUARTA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer Parentesis Izq
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
					//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion,
					//entonces no hay que seguir buscando sino salir
					return false;
				}
				
				// Reconocer Factor1
				do {
					// Arreglo del loop. Hay que consumir la coma.
					if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {
						this.reconocerToken(IToken.TYPE_COMA);
					}
					
					if ( Expresion.primeros(this.getTokenSiguiente()) ) {
						//<FACTOR1> -> <EXP> , <FACTOR1>
						
						// Reconocer <Expresion>
						Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
						expresion.setEnvName(this.envName);
						
						recognized &= expresion.reconocer();
						this.setValidated(expresion.validado());
						
						// ASEM-TYPE_CHECK.
						typeFactorList.add(expresion.getTypeExpresion());
						
						// ASEM-TREE
						this.parametros.add(expresion.getTreeNode());
					}
				} while (this.getTokenSiguiente().getType() == IToken.TYPE_COMA);
				
				
				// Reconocer Parentesis Der
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
					return false;
				}
				
				// ASEM-TYPE_CHECK.
				this.accept(TypeCheckVisitor.getInstance());
				
				// ASEM-TREE
				this.accept(TreeVisitor.getInstance());
				
			} else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_CORCHETE_IZQ)) ) {
				
				// <FACTOR> -> IDENTIFICADOR [ <EXP> ]
				
				this.setNroProduccion(PROD_QUINTA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
//				// Arreglos no deben declararse en Proc or Funcs.
//				if ("".equals(this.getEnvName())){
//					return false;
//				}
				
				// Reconocer Parentesis Izq
				if ( !this.reconocerToken(IToken.TYPE_CORCHETE_IZQ) ) {
					return false;
				}
				
				this.accept(TreeVisitor.getInstance());		//Busco el arreglo con el IDENTIFICADOR y lo cuelgo en el nodo actual
				
				// Reconocer Expresion
				Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
				expresion.setEnvName(this.envName);
				
				recognized &= expresion.reconocer();
				this.setValidated(expresion.validado());
				
				// Reconocer Parentesis Der
				if ( !this.reconocerToken(IToken.TYPE_CORCHETE_DER) ) {
					return false;
				}
				
				// ASEM-TYPE_CHECK.
				this.typeExpresion = expresion.getTypeExpresion();
				this.accept(TypeCheckVisitor.getInstance());

				// ASEM-TREE
				this.getTreeNode().addChild(expresion.getTreeNode());	//Le cuelgo al arreglo la expresion que resuelve su subindice
				
			} else {
				// <FACTOR> -> IDENTIFICADOR
				
				this.setNroProduccion(PROD_SEXTA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				this.accept(TreeVisitor.getInstance());
				
				// ASEM-TYPE_CHECK.
				this.accept(TypeCheckVisitor.getInstance());
			}
		} else {
			
			//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
			em.error("Se esperaba un factor", this.getTokenSiguiente());
			
			//Tratamiento de errores
			em.recover(this.getClass(), this.tokenStream);
			return false;
		}
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.TYPE_PARENTESIS_IZQ) ) ||
				token.equals( new Token(IToken.TYPE_NUM_ENTERO) ) ||
				token.equals( new Token(IToken.TYPE_NUM_NATURAL) ) ||
				token.equals( new Token(IToken.TYPE_IDENTIFICADOR) );
	}
	
	
	public int getTypeFactor() {
		return typeFactor;
	}
	public void setTypeFactor(int tf) {
		typeFactor = tf;
	}
	public String getEnvName() {
		return envName;
	}
	public int getTypeExpresion() {
		return typeExpresion;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public int getNumValue() {
		return numValue;
	}
	public ArrayList<Integer> getTypeFactorList() {
		return typeFactorList;
	}
	public String getIdentificador() {
		return identificador;
	}
	public List<TreeNode> getParametros() {
		return parametros;
	}
	
}
