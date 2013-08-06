package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.label.LabelGenerator;

public class Mientras extends AbstractElement {

	public Mientras (){
		this.label = "MIENTRAS";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Mientras))
			return false;
		Mientras other = (Mientras) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {
		String startLabel = LabelGenerator.getInstance().generateLabel();
		sb.append(startLabel + ":" + ENTER);
		String endWhile = LabelGenerator.getInstance().generateLabel();
				
		//Condicion...
		this.getChildren().get(0).compile(sb);
		//Codigo de adentro del while
		this.getChildren().get(1).compile(sb);
		sb.append(TAB + "jmp " + startLabel + ENTER);
		
		sb.append(endWhile + ":" + ENTER);
	}
}
