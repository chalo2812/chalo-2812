package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

public class Parametro extends SimboloGenerico {

	public static final int BYREF = 0;
	public static final int BYVAL = 1;

	private int mode; // Solo para Parametros.
	private int memoryInStack;

	public Parametro(String name, int type, int mode) {
		super(name, type);
		this.mode = mode;
		this.label = "PARAMETRO";
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public int getMemoryInStack() {
		return memoryInStack;
	}

	public void setMemoryInStack(int memoryInStack) {
		this.memoryInStack = memoryInStack;
	}

	@Override
	public String toString() {
		return this.getLabel() + ": " + this.getName() + " (" + this.getEnvName() + " "
				+ SymbolTable.getInstance().getVerboseType(this.getType()) + ") = " 
				+ ((this.getMode() == BYREF) ? "BYREF" : "BYVAL");
	}

	@Override
	public void compile (StringBuilder sb) {
			sb.append(TAB + "mov ax, " + this.place + ENTER);
	}

}
