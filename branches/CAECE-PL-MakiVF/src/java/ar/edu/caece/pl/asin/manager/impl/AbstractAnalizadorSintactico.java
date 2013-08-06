package ar.edu.caece.pl.asin.manager.impl;

import ar.edu.caece.pl.alex.model.IDictionary;
import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Dictionary;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asem.model.IVisitor;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.IErrorManager;
import ar.edu.caece.pl.asin.model.ITokenStream;

public abstract class AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	
	/* ATRIBUTOS GENERALES*/
	private int nroProduccionActual;  //Nro de produccion a loguear
	private boolean validated = true;	// Validacion semántica de una producción.
	private TreeNode treeNode = new TreeNode();
	protected boolean recognized = true; 
	
	/* ACTORES PRINCIPALES */
	protected IErrorManager em;
	protected ITokenStream tokenStream;
	protected SymbolTable symbolTable = SymbolTable.getInstance();
	
	/* ELEMENTOS AUXILIARES */
	protected IDictionary dic = Dictionary.getInstance();
	private static final String INIT_ERROR = "No se asigno fuente para leer";
	protected boolean debugMode;
	
	/* INICIALIZACION */
	public AbstractAnalizadorSintactico ( ITokenStream ts, boolean debugMode ) {
		
		if (ts == null) {
			
			throw new IllegalArgumentException( INIT_ERROR );
		}
		
		this.tokenStream = ts;
		this.debugMode = debugMode;
		
		//Inicializo el ErrorManager
		em= ErrorManager.getInstance(debugMode);
	}

	/* TEMPLATE METHOD */
	public abstract boolean reconocer();
	
	/* FUNCIONES AUXILIARES */
	protected IToken getTokenActual() {
		
		return this.tokenStream.getTokenActual();
	}
	
	public IToken getTokenSiguiente() { //Pasa a ser public para el visitor
		
		return this.tokenStream.getTokenSiguiente();
	}
	
	/** Lee y debuguea. Ademas es privado porque solo lo llamara this.reconocerToken() */
	private void avanzar() {
		// Avanzo
		tokenStream.leer();

		//Agrego info de debug del token leido
		em.debug( this.getTokenActual() );
	}
	
	protected boolean reconocerToken(int tokenType) {
		
		return (this.dic.isValid(tokenType))?
			this.reconocerToken(new Token(tokenType)):
			false;
	}
	
	protected boolean reconocerToken(String palabraReservada) {
		
		return (this.dic.isValid(palabraReservada))?
				this.reconocerToken(new Token(palabraReservada)):
				false;
	}
	
	private boolean reconocerToken(IToken token) {
		
		boolean recognized = true;
		
		if (this.getTokenSiguiente().equals(token)) {
			
			this.avanzar();
			
		} else {
			
			em.error("Se esperaba "+token.getTokenText(), this.getTokenSiguiente());
			recognized = false;
			
			em.recover(this.getClass(), this.tokenStream);
		}
		return recognized;
	}
	
	/** TP3 - Visitor
	 * Metodo de entrada del Visitor a la clase No Terminal
	 * La idea es no generar el arbol (TreeVisitor), si el input no pasó las validaciones sintácticas.
	 * @param visitor qué visitor hacer pasar.
	 * @param recognized 
	 */
	public void accept(IVisitor visitor) {
		
		// No armo el arbol, en caso de que no haya podido reconocer correctamente el lenguaje
		if ( !(visitor instanceof TreeVisitor) || this.recognized){
			visitor.visit(this);
		}
	}
	

	public int getNroProduccion() {
		return nroProduccionActual;
	}

	public void setNroProduccion(int nroProduccion) {
		this.nroProduccionActual = nroProduccion;
	}
	
	/* METODOS QUE COMPROMETEN LA ENCAPSULACION PROVOCADO POR EL VISITOR*/
	
	public IErrorManager getErrorManager() {
		return this.em;
	}
	
	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}
	public TreeNode getTreeNode() {
		return this.treeNode;
	}

	public boolean validado() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = this.validated && validated;
	}
}
