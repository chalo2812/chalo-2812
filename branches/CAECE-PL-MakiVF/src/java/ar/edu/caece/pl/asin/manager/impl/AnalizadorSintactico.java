package ar.edu.caece.pl.asin.manager.impl;

import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.Codigo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Declaraciones;
import ar.edu.caece.pl.asem.model.impl.treeelements.Programa;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.visitors.BuildSymbolTableVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.LoggingVisitor;
import ar.edu.caece.pl.asem.model.impl.visitors.TreeVisitor;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.Header;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;

public class AnalizadorSintactico extends AbstractAnalizadorSintactico implements IAnalizadorSintactico {
	/*ATRIBUTOS*/
	private TreeNode nodoDeclaraciones = new TreeNode(new Declaraciones());	//Sintetizado
	private TreeNode nodoCodigo = new TreeNode(new Codigo());					//Sintetizado

	private List<SimboloGenerico> declaraciones; 	//Sintetizado
	private List<TreeNode> listaMetodos;	//Sintetizado

	/* INICIALIZACION */	
	public AnalizadorSintactico ( ITokenStream ts ) {
		this(ts, false);
	}
	public AnalizadorSintactico ( ITokenStream ts, boolean debugMode ) {
		super(ts,debugMode);
		this.getTreeNode().setData(new Programa());
	}
	
	/**
	 * Devuelve true si reconoce toda la sarta
	 */
	public boolean reconocer () {
		
		cleanAll();
		
		boolean recognized = true; //(asumimos correctitud hasta demostrar lo contrario)
		
		this.setNroProduccion(PROD_ENCABEZADO);
		this.accept(LoggingVisitor.getInstance());	//Loguea encabezado
		this.accept(BuildSymbolTableVisitor.getInstance());	//Crea ambiente global
		/* <PROGRAMA> -> <HEADER> <PROCS_FUNCS> EOF */

		this.setNroProduccion(PROD_PRIMERA);	//Lo preciso para saber qué agregar en el árbol
		
		// Reconocer Header
		Header h = new Header(this.tokenStream, this.debugMode);
		recognized &= h.reconocer();
		this.setValidated(h.validado());
		
		this.declaraciones = h.getDeclaraciones();

		this.accept(BuildSymbolTableVisitor.getInstance());
		this.accept(TreeVisitor.getInstance());
		
		this.setNroProduccion(PROD_SEGUNDA);	//Lo preciso para saber qué agregar en el árbol
		
		// Reconocer Procedimientos o Funciones
		ProcsFuncs pfs = new ProcsFuncs(this.tokenStream,this.debugMode);
		
		recognized &= pfs.reconocer();
		this.setValidated(pfs.validado());
		
		//this.nodoMetodoGenericoTemporal = pfs.getTreeNode();
		this.listaMetodos = pfs.getListaMetodos();
		this.accept(TreeVisitor.getInstance());	//Agregar todas los métodos al nodo codigo
		
		
		this.setNroProduccion(PROD_TERCERA);	//Lo preciso para saber qué agregar en el árbol
		
		//En este punto deberia encontrar TOKEN_FIN_DE_ARCHIVO
		recognized &= this.reconocerToken(IToken.TYPE_FIN_DE_ARCHIVO);
		
		this.accept(BuildSymbolTableVisitor.getInstance());	//Validar si se incorporó el metodo main
		this.accept(TreeVisitor.getInstance());		//Colgar el nodo declaraciones y codigo del root
		
		return recognized;
	}
	
	private void cleanAll() {
		SymbolTable.getInstance().cleanAll();
		this.em.cleanAll();
		
	}
	public List<SimboloGenerico> getDeclaraciones() {
		return declaraciones;
	}
	public TreeNode getNodoDeclaraciones() {
		return nodoDeclaraciones;
	}
	public TreeNode getNodoCodigo() {
		return nodoCodigo;
	}
	public List<TreeNode> getListaMetodos() {
		return listaMetodos;
	}
}
