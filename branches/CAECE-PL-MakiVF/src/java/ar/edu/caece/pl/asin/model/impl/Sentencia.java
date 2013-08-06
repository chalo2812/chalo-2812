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

public class Sentencia extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	/*ATRIBUTOS*/
	private String envName;		//Nombre del entorno que contiene las constantes, variables, arreglos o parametros
	
	private int typeExpresion1;	// Tipo de dato de <EXP>
	private int typeExpresion2;	// Tipo de dato de <EXP>
	private ArrayList<Integer> typeFactorList = new ArrayList<Integer>();	// Lista de tipos de datos de <Factor1>
	
	//Atributos para asignaciones y llamadas a funcion o procedimiento
	private String identificador;	// Se guarda el nombre del Identificador.
	private List<TreeNode> parametros = new ArrayList<TreeNode>();	// Lista de parametros de una llamada a funcion ó procedimiento.
	private TreeNode nodoExpresion1;	// Nodo Expresion1.
	private TreeNode nodoExpresion2;	// Nodo Expresion1.
	
	//Atributos para el If y While
	private TreeNode nodoCondicion;
	private List<TreeNode> sentencias = new ArrayList<TreeNode>();	// Lista de sentencias anidadas en un cuerpo.
	
	//Atributos para el Show y ShowLn
	private List<TreeNode> elementos = new ArrayList<TreeNode>();	// Lista de elementos a mostrar por pantalla
	
	/* INICIALIZACION */	
	public Sentencia ( ITokenStream ts ) {
		super(ts, false);
	}
	public Sentencia ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce una sentencia individual
	 */
	public boolean reconocer () {

		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* 
		<SENTENCIA>-> IDENTIFICADOR           := <EXP> 				    		; <SENTENCIA>	|
		              IDENTIFICADOR [ <EXP> ] := <EXP> 				    		; <SENTENCIA>	|
		              IDENTIFICADOR ( <FACTOR_1> )     				    		; <SENTENCIA>	|
		              IF 	<CONDICION> THEN <SENTENCIA> ELSE <SENTENCIA> ENDIF ; <SENTENCIA> 	|
		              IF 	<CONDICION> THEN <SENTENCIA> ENDIF 					; <SENTENCIA> 	|
		              WHILE 	<CONDICION> DO   <SENTENCIA> END-WHILE 			; <SENTENCIA> 	|
		              SHOWLN <SHOW_ELEM> 						    			; <SENTENCIA>	|
		              SHOW   <SHOW_ELEM>						    			; <SENTENCIA> 	|
		              READ   IDENTIFICADOR						    			; <SENTENCIA> 	|
		              															; <SENTENCIA> 	|
		       		  LAMBDA																	*/
		
		if ( this.getTokenSiguiente().getType() == IToken.TYPE_IDENTIFICADOR ) {
			
			/* <SENTENCIA> ->
		  	  IDENTIFICADOR := <EXP>           ; <SENTENCIA> |
			  IDENTIFICADOR [ <EXP> ] := <EXP> ; <SENTENCIA> |
			  IDENTIFICADOR ( <FACTOR_1> )     ; <SENTENCIA> */
			
			// ASEM-TREE
			this.identificador = this.getTokenSiguiente().getTokenText();
			
			
			// Reconocer Identificador
			this.reconocerToken(IToken.TYPE_IDENTIFICADOR);
			
			
			if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_ASIGNACION) ) ) {
				//<SENTENCIA> -> IDENTIFICADOR := <EXP>           ; <SENTENCIA>
				
				this.setNroProduccion(PROD_PRIMERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer ASIGNACION
				recognized &= this.reconocerToken(IToken.TYPE_ASIGNACION);
				
				// Reconocer <Expresion>
				Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
				expresion.setEnvName(this.envName);
				
				recognized &= expresion.reconocer();
				this.setValidated(expresion.validado());
				
				this.nodoExpresion1 =  expresion.getTreeNode();
				
				// ASEM-TYPE_CHECK.
				this.typeExpresion1 = expresion.getTypeExpresion();
				this.accept(TypeCheckVisitor.getInstance());
								
				// ASEM-TREE
				this.accept(TreeVisitor.getInstance());
				
			} else if ( this.getTokenSiguiente().getType() == IToken.TYPE_CORCHETE_IZQ ) {
				
				//<SENTENCIA> -> IDENTIFICADOR [ <EXP> ] := <EXP> ; <SENTENCIA>
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer CORCHETE_IZQ
				recognized &= this.reconocerToken(IToken.TYPE_CORCHETE_IZQ);
				
				// Reconocer <Expresion>
				Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
				expresion.setEnvName(this.envName);
				
				recognized &= expresion.reconocer();
				this.setValidated(expresion.validado());
				
				// Reconocer CORCHETE_DER
				recognized &= this.reconocerToken(IToken.TYPE_CORCHETE_DER);
				
				// Reconocer ASIGNACION
				recognized &= this.reconocerToken(IToken.TYPE_ASIGNACION);
				
				// Reconocer <Expresion>
				Expresion expresion2 = new Expresion(this.tokenStream,this.debugMode);
				expresion2.setEnvName(this.envName);
				
				recognized &= expresion2.reconocer();
				this.setValidated(expresion2.validado());
				
				// ASEM-TYPE_CHECK.
				this.typeExpresion1 = expresion.getTypeExpresion();
				this.typeExpresion2 = expresion2.getTypeExpresion();
				this.accept(TypeCheckVisitor.getInstance());
				
				// ASEM-TREE
				this.nodoExpresion1 =  expresion.getTreeNode();
				this.nodoExpresion2 =  expresion2.getTreeNode();
				this.accept(TreeVisitor.getInstance());
				
			} else if ( this.getTokenSiguiente().equals( new Token(IToken.TYPE_PARENTESIS_IZQ) ) ) {
				
				//<SENTENCIA> -> IDENTIFICADOR ( <FACTOR_1> )     ; <SENTENCIA>
				
				this.setNroProduccion(PROD_TERCERA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer PARENTESIS_IZQ
				recognized &= this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ);
				
				// Reconocer <FACTOR_2>
				do {
					
					if(this.getTokenSiguiente().getType() == IToken.TYPE_COMA) {	//Arreglo del loop.
						this.reconocerToken(IToken.TYPE_COMA);						//Hay que consumir la coma.
					}
					
					if ( Expresion.primeros(this.getTokenSiguiente()) ) {
						//<FACTOR_1> -> <EXP> , <FACTOR_1>
						
//						this.setNroProduccion(PROD_PRIMERA);
//						this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
						
						// Reconocer <Expresion>
						Expresion expresion = new Expresion(this.tokenStream,this.debugMode);
						expresion.setEnvName(this.envName);
						
						recognized &= expresion.reconocer();
						this.setValidated(expresion.validado());
						
						// ASEM-TYPE_CHECK.
						this.typeFactorList.add(expresion.getTypeExpresion());
						
						// ASEM-TREE
						this.parametros.add(expresion.getTreeNode());
					}
				} while (this.getTokenSiguiente().getType() == IToken.TYPE_COMA);
				
				// Reconocer PARENTESIS_DER
				recognized &= this.reconocerToken(IToken.TYPE_PARENTESIS_DER);
				
				// ASEM-TYPE_CHECK.
				this.accept(TypeCheckVisitor.getInstance());
				
				//ASEM-TREE
				this.accept(TreeVisitor.getInstance());
				
			} else {
				//No deberia darse el caso, pero si se llama a reconocer() en un estado incorrecto deberia salir por aqui
				em.error("Se esperaba " + this.dic.getTokenDescription(IToken.TYPE_ASIGNACION) +
						          " o " + this.dic.getTokenDescription(IToken.TYPE_CORCHETE_IZQ) +
						          " o " + this.dic.getTokenDescription(IToken.TYPE_PARENTESIS_IZQ),
						this.getTokenSiguiente());
				
				recognized = false;
				
				//Tratamiento de errores
				em.recover(this.getClass(), this.tokenStream);
			}
			
			// <-- Reconocer <sentencia1>
			
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
			//Cierra el bucle de procs funcs o sentencia
			
		} else if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_IF) ) ) {
			
			//<SENTENCIA> -> IF <CONDICION> THEN <SENTENCIA> <ELSE> ENDIF ; <SENTENCIA>
			
			this.setNroProduccion(PROD_CUARTA);
			this.accept(LoggingVisitor.getInstance());	
			
			// Reconocer IF
			this.reconocerToken(IToken.PALABRA_RESERVADA_IF);
			
			// Reconocer <Condicion>
			Condicion condicion = new Condicion(this.tokenStream,this.debugMode);
			condicion.setEnvName(this.envName);
			
			recognized &= condicion.reconocer();
			this.setValidated(condicion.validado());
			
			//ASEM-TREE
			this.nodoCondicion = condicion.getTreeNode();

			// Reconocer THEN
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_THEN) ) {
				return false;
			}
			
			// Reconocer varias <SENTENCIA>
			while ( Sentencia.primeros( this.getTokenSiguiente() ) ) {
				
				Sentencia sentencia = new Sentencia(this.tokenStream, this.debugMode);
				sentencia.setEnvName(this.envName);
				
				recognized &= sentencia.reconocer();
				this.setValidated(sentencia.validado());
				
				//ASEM-TREE
				this.sentencias.add(sentencia.getTreeNode());
			}
			
			//ASEM-TREE: Completar la primera parte del IF
			this.accept(TreeVisitor.getInstance());
			
			//ELSE
			if ( this.getTokenSiguiente().equals(new Token(IToken.PALABRA_RESERVADA_ELSE) ) ) {
				// <ELSE> -> ELSE <SENTENCIAS>
				
				this.setNroProduccion(PROD_QUINTA);
				this.accept(LoggingVisitor.getInstance());	
				
				// Reconocer ELSE
				this.reconocerToken(IToken.PALABRA_RESERVADA_ELSE);
				
				// Reconocer varias <SENTENCIA>
				while ( Sentencia.primeros( this.getTokenSiguiente() ) ) {
					
					Sentencia sentencia = new Sentencia(this.tokenStream, this.debugMode);
					sentencia.setEnvName(this.envName);
					
					recognized &= sentencia.reconocer();
					this.setValidated(sentencia.validado());
					
					//ASEM-TREE
					this.sentencias.add(sentencia.getTreeNode());
				}
				
				//ASEM-TREE: Completar el Else
				this.accept(TreeVisitor.getInstance());
			}
			// <-- Reconocer <ELSE>
			
			// Reconocer ENDIF
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_END_IF) ) {
				return false;
			}
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
		} else if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_WHILE) ) ) {
			
			//<SENTENCIA> -> WHILE <EXP> <C'> DO <SENTENCIA> END-WHILE ; <SENTENCIA> 
			
			this.setNroProduccion(PROD_SEXTA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual	
			
			// Reconocer WHILE
			this.reconocerToken(IToken.PALABRA_RESERVADA_WHILE);
			
			// Reconocer <Condicion>
			Condicion condicion = new Condicion(this.tokenStream,this.debugMode);
			condicion.setEnvName(this.envName);
			
			recognized &= condicion.reconocer();
			this.setValidated(condicion.validado());
			
			//ASEM-TREE
			this.nodoCondicion = condicion.getTreeNode();
			
			// Reconocer DO
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_DO) ) {
				return false;
			}
			
			// Reconocer varias <SENTENCIA>
			while ( Sentencia.primeros( this.getTokenSiguiente() ) ) {
				
				Sentencia sentencia = new Sentencia(this.tokenStream, this.debugMode);
				sentencia.setEnvName(this.envName);
				
				recognized &= sentencia.reconocer();
				this.setValidated(sentencia.validado());
				
				//ASEM-TREE
				this.sentencias.add(sentencia.getTreeNode());
			}	
			
			// Reconocer END-WHILE
			if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_END_WHILE) ) {
				return false;
			}
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
			//ASEM-TREE
			this.accept(TreeVisitor.getInstance());
			
		} else if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_SHOWLN ) ) ) {
			
			// <SENTENCIA> -> SHOWLN <SHOW_ELEM> 						    ; <SENTENCIA>
			
			this.setNroProduccion(PROD_SEPTIMA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual		
			
			// Reconocer SHOWLN
			this.reconocerToken(IToken.PALABRA_RESERVADA_SHOWLN);
			
			// Reconocer <SHOWELEM>
						
			ShowElem showElem = new ShowElem(this.tokenStream, this.debugMode);
			showElem.setEnvName(this.envName);
			
			recognized &= showElem.reconocer();
			this.setValidated(showElem.validado());
			
			//ASEM-TREE
			this.elementos = showElem.getElementos();
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
			//ASEM-TREE
			this.accept(TreeVisitor.getInstance());
			
		} else if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_SHOW) ) ) {
			
			// <SENTENCIA> -> SHOW   <SHOW_ELEM>	; <SENTENCIA>
			
			this.setNroProduccion(PROD_OCTAVA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual		
			
			// Reconocer SHOW
			this.reconocerToken(IToken.PALABRA_RESERVADA_SHOW);
			
			// Reconocer <SHOWELEM>
			ShowElem showElem = new ShowElem(this.tokenStream, this.debugMode);
			showElem.setEnvName(this.envName);
			
			recognized &= showElem.reconocer();
			this.setValidated(showElem.validado());
			
			//ASEM-TREE
			this.elementos = showElem.getElementos();
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
			//ASEM-TREE
			this.accept(TreeVisitor.getInstance());


		} else if ( this.getTokenSiguiente().equals( new Token (IToken.PALABRA_RESERVADA_READ) ) ) {
			
			// <SENTENCIA> -> READ   IDENTIFICADOR	; <SENTENCIA>
			
			this.setNroProduccion(PROD_NOVENA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual		
			
			// Reconocer READ
			this.reconocerToken(IToken.PALABRA_RESERVADA_READ);
			
			// Reconocer IDENTIFICADOR
			if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
				return false;
			}
			
			//ASEM-TREE
			this.identificador = this.getTokenActual().getTokenText();
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
			
			//ASEM-TREE
			this.accept(TreeVisitor.getInstance());
			
		} else if ( this.getTokenSiguiente().equals( new Token (IToken.TYPE_PUNTO_COMA) ) ) {
			
			// <SENTENCIA> -> ; <SENTENCIA>
			
			this.setNroProduccion(PROD_DECIMA);
			this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual	
			
			// Reconocer punto y coma
			if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
				return false;
			}
		
		}
		
		return recognized;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.TYPE_IDENTIFICADOR) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_IF) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_WHILE) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_SHOWLN) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_SHOW) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_READ) ) ||
				token.equals( new Token(IToken.TYPE_PUNTO_COMA) );
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getIdentificador() {
		return identificador;
	}
	public List<TreeNode> getParametros() {
		return parametros;
	}
	public TreeNode getNodoExpresion1() {
		return nodoExpresion1;
	}
	public TreeNode getNodoExpresion2() {
		return nodoExpresion2;
	}
	public int getTypeExpresion1() {
		return typeExpresion1;
	}
	public int getTypeExpresion2() {
		return typeExpresion2;
	}
	public ArrayList<Integer> getTypeFactorList() {
		return typeFactorList;
	}
	public TreeNode getNodoCondicion() {
		return nodoCondicion;
	}
	public List<TreeNode> getSentencias() {
		return sentencias;
	}
	public List<TreeNode> getElementos() {
		return elementos;
	}
}
