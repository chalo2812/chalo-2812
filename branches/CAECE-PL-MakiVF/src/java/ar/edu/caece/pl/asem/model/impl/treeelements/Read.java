package ar.edu.caece.pl.asem.model.impl.treeelements;


public class Read extends AbstractElement {

	public Read (){
		this.label = "LEER";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Read))
			return false;
		Read other = (Read) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {

		Variable variable = (Variable) this.getChildren().get(0);
		String localContextName = variable.getEnvName();
		String id = localContextName + "_" + variable.getName();
		
		sb.append(TAB + "PUSH 0000h" + ENTER);
		sb.append(TAB + "PUSH 0001h" + ENTER);
		sb.append(TAB + "CALL readln" + ENTER);
		sb.append(TAB + "POP Ax" + ENTER);
		sb.append(TAB + "MOV " + id + ", AX" + ENTER);
	}
	
}
