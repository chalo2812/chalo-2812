package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

public class Funcion extends MetodoGenerico {

	private int tempResultFun;

	public Funcion(String name) {
		this.name = name;
		this.label = "FUNCION";
	}

	public int getTemp() {
		return tempResultFun;
	}

	public void setTemp(int tempResultFun) {
		this.tempResultFun = tempResultFun;
	}

	@Override
	public String toString() {
		return this.getLabel()
				+ ": "
				+ this.getName()
				+ "() : "
				+ SymbolTable.getInstance()
						.getVerboseType(this.getReturnType());
	}
}
