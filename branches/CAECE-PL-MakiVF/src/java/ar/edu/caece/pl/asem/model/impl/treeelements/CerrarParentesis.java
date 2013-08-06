package ar.edu.caece.pl.asem.model.impl.treeelements;

public class CerrarParentesis extends AbstractElement {

	public CerrarParentesis (){
		this.label = "ELSE";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CerrarParentesis))
			return false;
		CerrarParentesis other = (CerrarParentesis) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
