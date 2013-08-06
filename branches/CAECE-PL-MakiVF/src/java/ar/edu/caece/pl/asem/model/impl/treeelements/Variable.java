package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

public class Variable extends SimboloGenerico {

	public Variable(String name, int type) {
		super(name, type);
		this.label = "VARIABLE";
	}

	@Override
	public String toString() {
		return this.getLabel() + ": " + this.getName() + " (" + this.getEnvName() + " " + SymbolTable.getInstance().getVerboseType(this.getType()) + ")";
	}

	@Override
	public void compile(StringBuilder sb) {
		sb.append(TAB + "mov ax, " + this.place + ENTER);
	}
	
}
