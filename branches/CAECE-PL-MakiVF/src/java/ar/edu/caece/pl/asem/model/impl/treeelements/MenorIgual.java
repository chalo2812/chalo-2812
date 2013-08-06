package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.label.LabelGenerator;

public class MenorIgual extends AbstractElement {

	public MenorIgual (){
		this.label = "CONDICION <=";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MenorIgual))
			return false;
		MenorIgual other = (MenorIgual) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile (StringBuilder sb) {
		LabelGenerator.getInstance().getActualLabel();

		// Codigo expression izquierda
		this.getChildren().get(0).compile(sb);
		sb.append(TAB + "mov cx, ax " + ENTER);
		
		// Codigo expression derecha
		this.getChildren().get(1).compile(sb);
		sb.append(TAB + "mov bx, ax" + ENTER);
		sb.append(TAB + "mov ax, cx" + ENTER);
		sb.append(TAB + "cmp ax, bx" + ENTER);

		String actualLabel = LabelGenerator.getInstance().getActualLabel();
		sb.append(TAB + "jg " + actualLabel + ENTER);
	}
}
