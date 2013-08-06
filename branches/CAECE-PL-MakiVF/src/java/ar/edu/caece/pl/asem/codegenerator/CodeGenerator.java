package ar.edu.caece.pl.asem.codegenerator;

import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
import ar.edu.caece.pl.asem.model.impl.treeelements.Programa;
import ar.edu.caece.pl.asem.model.tree.Tree;

public class CodeGenerator {

	//Singleton
	private static CodeGenerator instance = new CodeGenerator();
	private CodeGenerator(){}
	public static CodeGenerator getInstance() { return instance; }

	private Tree<AbstractElement> semanticTree;

	public String generateCode() {
		StringBuilder code = new StringBuilder();
		
		/*
		 PROGRAMA
		  DECLARACIONES
		  CODIGO
		    PROCEDIMIENTO: main()
		      PARAMETROS
		      DECLARACIONES
		      RETORNO: VOID
		      CUERPO
		 */

		Programa program = (Programa)semanticTree.getRoot().getData();
		program.compile(code);		
		
		System.out.println(code);
		return code.toString();
	}

	public void setTree(Tree<AbstractElement> semanticTree) {
		this.semanticTree = semanticTree;
	}
}
