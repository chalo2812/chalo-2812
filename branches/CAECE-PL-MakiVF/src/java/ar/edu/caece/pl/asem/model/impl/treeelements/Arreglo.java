package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.temp.TemporalGenerator;

public class Arreglo extends SimboloGenerico {

	private int length; // Solo para arrays

	public Arreglo(String name, int type, int length) {
		super(name, type);
		this.length = length;
		this.label = "ARREGLO";
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return this.getLabel() + ": " + this.getName() + " ("
				+ SymbolTable.getInstance().getVerboseType(this.getType()) + ") = "
				+ this.getLength();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arreglo other = (Arreglo) obj;
		if (length != other.length)
			return false;
		return true;
	}

	@Override
	public void compile(StringBuilder sb) {
		TemporalGenerator.getInstance().generateTemporal();
		this.place = TemporalGenerator.getInstance().getActualTemporal();
		
		if (this.getParent() instanceof ShowLn
		 || this.getParent() instanceof Show
		 || this.getParent() instanceof Asignacion) {
			AbstractElement child = this.getChildren().get(0);
			child.compile(sb);
		} else {
			// TODO hacer esto mejor, porque esto se hace por los calculos aritmeticos en los indices de los arreglos
			AbstractElement child = this.getChildren().get(0);
			child.compile(sb);
			sb.append("\t mov bx, ax \n");
			sb.append("\t mov ax, 2 \n");
			sb.append("\t mul bx \n");
			sb.append("\t mov bx, ax \n");
			sb.append("\t mov di, offset " + this.getEnvName() + "_" + this.getName() + " \n");
			sb.append("\t add di, bx \n");
			sb.append("\t mov ax, [di] \n");
			sb.append(TAB + "mov " + this.place + ",ax" + ENTER);
		} 
	}
}
