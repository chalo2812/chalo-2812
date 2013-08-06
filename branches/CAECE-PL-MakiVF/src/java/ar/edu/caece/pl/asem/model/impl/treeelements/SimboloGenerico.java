package ar.edu.caece.pl.asem.model.impl.treeelements;



public abstract class SimboloGenerico extends AbstractElement {

	private String name;
	private int type;	//INT o NAT
	private int value; // En Arreglo vale 0
	
	public SimboloGenerico(String name, int type) {
		this.name = name;
		this.type = type;
	}
	public SimboloGenerico(String name, int type, int value) {
		this(name,type);
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimboloGenerico other = (SimboloGenerico) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
