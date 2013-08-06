package ar.edu.caece.pl.asem.temp;

import java.util.HashMap;

import ar.edu.caece.pl.asem.model.impl.treeelements.MetodoGenerico;

public class TemporalGenerator {
	
	private static TemporalGenerator instance = null;
	private static final String ENTER = "\n";
	private MetodoGenerico checkPointMethod; 
	
	public static synchronized TemporalGenerator getInstance() {
		if (instance == null) {
			instance = new TemporalGenerator();
		}
		return instance;
	}
	
	private int index = 0;
	private int maxIndex = 0;
	private final String TEMPORAL = "tmp_";
	private HashMap<String,String> temporals = new HashMap<String, String>();
	
	public String generateTemporal() {
		String tmp = TEMPORAL + index; 
		index++;
		if (maxIndex < index) {
			maxIndex = index;
		}
		return tmp;
	}

	public String getActualTemporal() {
		return TEMPORAL + (index-1);
	}
	
	public String getTemporal() {
		return TEMPORAL + (index);
	}


	public String getActualM1Temporal() {
		return TEMPORAL + (index-2);
	}

	public void releaseTemporal() {
		if (index > 0) {
			index--;
		}
	}

	public void setTemporals(HashMap<String,String> temporals) {
		this.temporals = temporals;
	}

	public HashMap<String,String> getTemporals() {
		return temporals;
	}
	
	public String insertTemporal (String name, String value) {
		name = name + "p" + temporals.size();
		temporals.put(name, value);
		return name;
	}

	public String generateTemporalAssemblerCode() {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < maxIndex; i++) {
			sb.append(TEMPORAL + i + " dw ?" + ENTER);
		}
		for (String str : temporals.keySet()) {
			sb.append(str + " dw " + temporals.get(str) + ENTER);
		}
		
		return sb.toString();
	}
	
	public void cleanInstance(){
		this.instance = new TemporalGenerator();
	}
	
	/**
	 * Se guarda la referencia del metodo actual, para luego saber de quien hacer el volcado de pila, y recupero
	 * @param mg
	 */
	public void checkPointActualMethod(MetodoGenerico mg) {
		this.checkPointMethod = mg;
		
	}
	
	/**
	 * Metodo que permite recuperar la referencia del metodo actual, para hacer volcado y recupero de la pila
	 * @return
	 */
	public MetodoGenerico getActualMethod(){
		return this.checkPointMethod;
	}
	
}
