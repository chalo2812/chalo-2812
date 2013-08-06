package ar.edu.caece.pl.asin.model.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.visitors.BuildSymbolTableVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class ProcsFuncs extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	/* ATRIBUTOS */
	/*--De definicion del procedimiento o funcion*/
	private String envName;		//Nombre del entorno donde se estan creando las constantes, variables, arreglos o parametros
	private List<Parametro> parametros = new ArrayList<Parametro>();
	private int returnType;		//Si es una funcion indica el tipo de retorno (NAT o INT)
	private List<SimboloGenerico> declaraciones; //Sintetizado
	private List<TreeNode> listaSentenciasPorMetodo = new ArrayList<TreeNode>();
	private List<TreeNode> listaMetodos = new ArrayList<TreeNode>();
	private TreeNode nodoExpresion = new TreeNode();
	/*--Otros*/
	
	
	/* INICIALIZACION */	
	public ProcsFuncs ( ITokenStream ts ) {
		super(ts, false);
	}
	public ProcsFuncs ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
	}
	
	
	/** 
	 * Devuelve true si reconoce un bloque de procedimientos y funciones
	 */
	public boolean reconocer () {
		
		recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		
		/* <PROCS_FUNCS> ->
		 *		PROCEDURE IDENTIFICADOR(<BLOQUE_VAR_PARAM>);        <HEADER> BEGIN <SENTENCIA> END-PROC       ; <PROCS_FUNCS> |
		 *		FUNCTION  IDENTIFICADOR(<BLOQUE_VAR_PARAM>): <TIPO>;<HEADER> BEGIN <SENTENCIA> END FUNC <EXP> ; <PROCS_FUNCS> */
		
		while ( ProcsFuncs.primeros(this.getTokenSiguiente()) ) {
			
			cleanLocalAttributes();
			
			if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_PROCEDURE) ) ) {
				
				// <PROCS_FUNCS> -> PROCEDURE IDENTIFICADOR ( <BLOQUE_VAR_PARAM> ) ; <HEADER> BEGIN <SENTENCIA> END-PROC ; <PROCS_FUNCS>
				
				this.setNroProduccion(PROD_PRIMERA);
				this.returnType = SymbolTable.VOID;	//No retorna nada
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer Palabra Reservada "procedure"
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_PROCEDURE) ) {
					//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion del ProcsFuncs,
					//entonces no hay que seguir buscando sino salir
					return false;
				}
				
				// Reconocer Identificador
				if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
					return false;
				}
				
				this.envName = this.getTokenActual().getTokenText();	//El ambiente tendra el nombre de este procedimiento
				
				// Reconocer Parentesis izquierdo
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
					return false;
				}
				
				// Reconocer BloqueVarParam - todos los parámetros
				if ( BloqueVarParam.primeros( this.getTokenSiguiente() ) ) {
					
					BloqueVarParam bloqueVarParam = new BloqueVarParam(this.tokenStream, this.debugMode);
					recognized &= bloqueVarParam.reconocer();
					this.setValidated(bloqueVarParam.validado());
					
					this.parametros = bloqueVarParam.getParametros();
				}
				
				// Reconocer Parentesis derecho
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
					return false;
				}
				
				// Reconocer punto y coma
				if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
					return false;
				}
				
				// Reconocer <HEADER>
				Header header = new Header(this.tokenStream, this.debugMode);
				//header.setEnvName(this.envName);
				recognized &= header.reconocer();
				this.setValidated(header.validado());
				
				this.declaraciones = header.getDeclaraciones();
				
				this.accept(BuildSymbolTableVisitor.getInstance());		//Crear el procedimiento, sus parametros y vars/consts/arreglos
				
				// Reconocer token begin
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_BEGIN) ) {
					return false;
				}
				
				
				// Reconocer varias <SENTENCIA>
				while ( Sentencia.primeros( this.getTokenSiguiente() ) ) {

					Sentencia sentencia = new Sentencia(this.tokenStream, this.debugMode);
					sentencia.setEnvName(this.envName);
					
					recognized &= sentencia.reconocer();
					this.setValidated(sentencia.validado());
					
					this.listaSentenciasPorMetodo.add(sentencia.getTreeNode());
				}
				
				
				// Reconocer end-proc
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_END_PROC) ) {
					return false;
				}
				
				// Reconocer punto y coma
				if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
					return false;
				}
				
				this.accept(TreeVisitor.getInstance());
				
			} else if ( this.getTokenSiguiente().equals( new Token(IToken.PALABRA_RESERVADA_FUNCTION) ) ) {
				
				// <PROCS_FUNCS> -> FUNCTION  IDENTIFICADOR ( <BLOQUE_VAR_PARAM> ) : <TIPO> ; <HEADER> BEGIN <SENTENCIA> END FUNC <EXP> ; <PROCS_FUNCS>
				
				this.setNroProduccion(PROD_SEGUNDA);
				this.accept(LoggingVisitor.getInstance());	//Loguea produccion actual
				
				// Reconocer Palabra Reservada "function"
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_FUNCTION) ) {
					//El reconocerToken() habria adelantado el tokenStream hasta salir de esta iteracion del ProcsFuncs,
					//entonces no hay que seguir buscando sino salir
					return false;
				}
				
				// Reconocer Identificador
				if ( !this.reconocerToken(IToken.TYPE_IDENTIFICADOR) ) {
					return false;
				}
				
				this.envName = this.getTokenActual().getTokenText();	//El ambiente tendra el nombre de esta funcion
				
				// Reconocer Parentesis izquierdo
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_IZQ) ) {
					return false;
				}
				
				// Reconocer BloqueVarParam
				if ( BloqueVarParam.primeros( this.getTokenSiguiente() ) ) {
					
					BloqueVarParam bloqueVarParam = new BloqueVarParam(this.tokenStream, this.debugMode);
					recognized &= bloqueVarParam.reconocer();
					this.setValidated(bloqueVarParam.validado());
					
					this.parametros = bloqueVarParam.getParametros();
				}
				
				// Reconocer Parentesis derecho
				if ( !this.reconocerToken(IToken.TYPE_PARENTESIS_DER) ) {
					return false;
				}
				
				// Reconocer dos puntos
				if ( !this.reconocerToken(IToken.TYPE_DOS_PUNTOS) ) {
					return false;
				}
				
				// Reconocer <tipo>
				Tipo tipo = new Tipo(this.tokenStream, this.debugMode);
				tipo.setTreeNode(this.getTreeNode());
				
				recognized &= tipo.reconocer();
				this.setValidated(tipo.validado());
				
				this.returnType = tipo.getType();
				
				// Reconocer punto y coma
				if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
					return false;
				}
				
				// Reconocer <HEADER>
				Header header = new Header(this.tokenStream, this.debugMode);
				//header.setEnvName(this.envName);
				recognized &= header.reconocer();
				this.setValidated(header.validado());
				
				this.declaraciones = header.getDeclaraciones();
				
				this.accept(BuildSymbolTableVisitor.getInstance());
				
				// Reconocer token begin
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_BEGIN) ) {
					return false;
				}
				
				// Reconocer varias <SENTENCIA>
				while ( Sentencia.primeros( this.getTokenSiguiente() ) ) {
					
					Sentencia sentencia = new Sentencia(this.tokenStream, this.debugMode);
					sentencia.setEnvName(this.envName);
					sentencia.setTreeNode(this.getTreeNode());
					
					recognized &= sentencia.reconocer();
					this.setValidated(sentencia.validado());

					this.listaSentenciasPorMetodo.add(sentencia.getTreeNode());
				}	
				
				// Reconocer end-func
				if ( !this.reconocerToken(IToken.PALABRA_RESERVADA_END_FUNC) ) {
					return false;
				}
				
				// Reconocer Exp
				if ( Expresion.primeros(this.getTokenSiguiente())) {
					
					Expresion exp = new Expresion(this.tokenStream, this.debugMode);
					exp.setEnvName(this.envName);
					exp.setTreeNode(this.getTreeNode());
					
					recognized &= exp.reconocer();
					this.setValidated(exp.validado());
					
					this.nodoExpresion = exp.getTreeNode();
					
				} else {
					
					em.error("Se esperaba Expresion", this.getTokenSiguiente());
					
					//Tratamiento de errores
					em.recover(this.getClass(), this.tokenStream);
					
					return false;
				}
				
				// Reconocer punto y coma
				if ( !this.reconocerToken(IToken.TYPE_PUNTO_COMA) ) {
					return false;
				}
				
				//ASEM-TREE
				this.accept(TreeVisitor.getInstance());
				/** Fin de ReconocerFuntion*/
				
			}
		}
		
		return recognized;
	}
	
	private void cleanLocalAttributes() {
		this.envName = null;
		parametros = new ArrayList<Parametro>();
		returnType = 0;
		declaraciones = null;
	}
	
	public static boolean primeros(IToken token) {
		return  token.equals( new Token(IToken.PALABRA_RESERVADA_PROCEDURE) ) ||
				token.equals( new Token(IToken.PALABRA_RESERVADA_FUNCTION) );
	}
	public String getEnvName() {
		return envName;
	}
	public String getName() {
		return envName;
	}
	public int getReturnType() {
		return returnType;
	}
	public List<Parametro> getParametros() {
		return parametros;
	}
	public List<SimboloGenerico> getDeclaraciones() {
		return declaraciones;
	}
	public List<TreeNode> getListaMetodos() {
		return listaMetodos;
	}
	public List<TreeNode> getListaSentenciasPorMetodo() {
		return listaSentenciasPorMetodo;
	}
	public TreeNode getNodoExpresion() {
		return nodoExpresion;
	}
}
