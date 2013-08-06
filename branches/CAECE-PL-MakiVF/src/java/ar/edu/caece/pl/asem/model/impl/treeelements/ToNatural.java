package ar.edu.caece.pl.asem.model.impl.treeelements;

public class ToNatural extends AbstractElement {

	public ToNatural (){
		this.label = "TO_NATURAL";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ToNatural))
			return false;
		ToNatural other = (ToNatural) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
