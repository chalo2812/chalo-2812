package ar.edu.caece.pl.asem.model.impl.treeelements;

public class ToInteger extends AbstractElement {

	public ToInteger (){
		this.label = "TO_INTEGER";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ToInteger))
			return false;
		ToInteger other = (ToInteger) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
