package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

public class Procedimiento extends MetodoGenerico {

	public Procedimiento(String name) {
		this.name = name;
		this.setReturnType(SymbolTable.VOID);
		this.label = "PROCEDIMIENTO";
	}

	@Override
	public String toString() {
		return this.getLabel() + ": " + this.getName() + "()";
	}
}
