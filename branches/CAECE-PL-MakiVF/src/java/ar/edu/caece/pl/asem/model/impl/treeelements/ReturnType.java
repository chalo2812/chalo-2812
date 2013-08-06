package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;

//Tipos para las Funciones y Procedimientos 

public class ReturnType extends AbstractElement {

	private int type;

	public ReturnType(int type) {
		super();
		this.type = type;
		switch (type) {
		case SymbolTable.VOID:
			this.setLabel("VOID");
			break;
		case SymbolTable.BOOLEAN:
			this.setLabel("BOOLEAN");
			break;
		case SymbolTable.INTEGER:
			this.setLabel("INTEGER");
			break;
		case SymbolTable.NATURAL:
			this.setLabel("NATURAL");
			break;
		default:
			this.setLabel("UNKNOWN");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ReturnType))
			return false;
		ReturnType other = (ReturnType) obj;
		if (this.label == null) {
			if (other.label != null)
				return false;
		} else if (!this.label.equals(other.label))
			return false;
		return true;
	}

	public int getType() {
		return type;
	}

	public String toString() {
		return "RETORNO: " + this.label;
	}

}
