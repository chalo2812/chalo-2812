package ar.edu.caece.pl.asem.model.impl.treeelements;

public class Cuerpo extends AbstractElement {

	public Cuerpo() {
		this.label = "CUERPO";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cuerpo))
			return false;
		Cuerpo other = (Cuerpo) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
