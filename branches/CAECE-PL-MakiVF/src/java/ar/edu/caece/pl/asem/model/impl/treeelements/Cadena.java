package ar.edu.caece.pl.asem.model.impl.treeelements;

public class Cadena extends AbstractElement {

	private String value;

	public Cadena (){
		this.label = "CONSTANTE_STRING";
	}
	
	public Cadena(String cadena) {
		this.value = cadena;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cadena))
			return false;
		Cadena other = (Cadena) obj;
		if (this.label == null) {
			if (other.label != null)
				return false;
		} else if (!this.label.equals(other.label))
			return false;
		return true;
	}
	
	public String toString() {
		return this.label + ": "+ this.value;
	}

	@Override
	public void compile(StringBuilder sb) {
		// TODO Auto-generated method stub
	}

	public String getValue() {
		return this.value;
	}
	
}
