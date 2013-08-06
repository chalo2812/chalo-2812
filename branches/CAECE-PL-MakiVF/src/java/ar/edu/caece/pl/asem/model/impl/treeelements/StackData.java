package ar.edu.caece.pl.asem.model.impl.treeelements;

public class StackData {
	
	private String value;
	private int numberOfRuns;
	
	public StackData() {
		super();
		this.value = "";
		this.numberOfRuns = 0;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getNumberOfRuns() {
		return numberOfRuns;
	}

	public void setNumberOfRuns(int numberOfRuns) {
		this.numberOfRuns = numberOfRuns;
	}

}
