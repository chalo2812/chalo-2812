package ar.edu.caece.pl.asem.label;

public class LabelGenerator {

	private static LabelGenerator instance = null;

	public static synchronized LabelGenerator getInstance() {
		if (instance == null) {
			instance = new LabelGenerator();
		}
		return instance;
	}

	private int index = 0;
	private final String LABEL = "lbl";

	public String generateLabel() {
		String lbl = LABEL + "_" + index;
		index++;
		return lbl;
	}

	public String getActualLabel() {
		return LABEL + "_" + (index-1);
	}

	public void releaseLabel() {
		index--;
	}

}
