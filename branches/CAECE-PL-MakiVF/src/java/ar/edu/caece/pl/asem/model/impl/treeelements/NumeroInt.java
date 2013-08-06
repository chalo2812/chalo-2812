package ar.edu.caece.pl.asem.model.impl.treeelements;



public class NumeroInt extends AbstractElement {

	private int value;
	
	public NumeroInt(int value) {
		this.value = value;
		this.label = "NUMERO_ENTERO";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NumeroInt))
			return false;
		NumeroInt other = (NumeroInt) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	public String toString() {
		return new String(label + ": " + value);
	}

	public int getValue() {
		return value;
	}

	@Override
	public void compile (StringBuilder sb) {
		sb.append(TAB + "mov ax, " + this.getValue() + ENTER);
	}
	
}
