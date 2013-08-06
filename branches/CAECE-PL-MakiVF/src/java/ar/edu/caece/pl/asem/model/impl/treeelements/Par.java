package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.label.LabelGenerator;

public class Par extends AbstractElement {

	public Par() {
		this.label = "ES_PAR";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Par))
			return false;
		Par other = (Par) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {
		// Codigo expression
		this.getChildren().get(0).compile(sb);
		sb.append(TAB + "mov bx, 2" + ENTER);
		sb.append(TAB + "mov dx, 0" + ENTER);
		sb.append(TAB + "idiv bx" + ENTER);
		sb.append(TAB + "cmp dx, 0" + ENTER);
		sb.append("jne \t " + LabelGenerator.getInstance().getActualLabel() + ENTER);
	}
}
