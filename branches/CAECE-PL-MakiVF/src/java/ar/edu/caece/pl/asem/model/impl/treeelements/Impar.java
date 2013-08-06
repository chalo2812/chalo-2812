package ar.edu.caece.pl.asem.model.impl.treeelements;

public class Impar extends AbstractElement {

	public Impar (){
		this.label = "ES_IMPAR";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Impar))
			return false;
		Impar other = (Impar) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

}
