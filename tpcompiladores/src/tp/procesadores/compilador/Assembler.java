package tp.procesadores.compilador;

import tp.procesadores.compilador.constante.Constants;

//import ar.edu.caece.pl.asem.model.impl.Ambiente;
//import ar.edu.caece.pl.asem.model.impl.SymbolTable;
//import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
//import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
//import ar.edu.caece.pl.asem.model.impl.treeelements.MetodoGenerico;
//import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
//import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class Assembler {


	public String start() {
		StringBuffer sb = new StringBuffer();
		sb.append("ORG 100h" + Constants.ENTER);
		sb.append("JMP main" + Constants.ENTER);
		return sb.toString();
	}

	public String generateGlobalMemory() {
		StringBuffer sb = new StringBuffer();
		sb.append("0, ");
		return sb.toString();
	}

	public String finish() {
		StringBuffer sb = new StringBuffer();
		// if(ErrorManager.debugEnabled) {
		// sb.append("; ASM-Start - Init" + ENTER);
		// }
		sb.append("RET");
		// sb.append("JMP main" + ENTER);RET
		// if(ErrorManager.debugEnabled) {
		// sb.append("; ASM-Start - Finish" + ENTER);
		// }
		return sb.toString();
	}

}
