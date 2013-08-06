package ar.edu.caece.pl.asem.model.impl.treeelements;

public class Codigo extends AbstractElement {

	public Codigo (){
		this.label = "CODIGO";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Codigo))
			return false;
		Codigo other = (Codigo) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
