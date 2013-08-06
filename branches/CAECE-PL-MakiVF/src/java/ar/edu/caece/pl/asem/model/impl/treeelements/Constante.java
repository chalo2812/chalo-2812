package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

public class Constante extends SimboloGenerico {

	public Constante(String name, int type, int value) {
		super(name, type, value);
		this.label = "CONSTANTE";
	}
	
	@Override
	public String toString() {
		return this.getLabel() + ": " + this.getName() + " ("
				+ SymbolTable.getInstance().getVerboseType(this.getType()) + ") = "
				+ this.getValue();
	}
	
	@Override
	public void compile(StringBuilder sb) {
		sb.append(TAB + "mov ax, " + this.getValue() + ENTER);
	}
}
