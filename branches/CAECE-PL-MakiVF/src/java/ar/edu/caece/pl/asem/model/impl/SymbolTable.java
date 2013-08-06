package ar.edu.caece.pl.asem.model.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Como tabla, tiene un conjunto de filas. Cada fila tendrá un cjto de columnas (variable)
 * Cada fila, será un objeto Ambiente. Habrá un único ambiente por procedimiento o función
 */
public class SymbolTable {
	
	/* CONSTANTES */
	public static final int VOID = -1;
	
	public static final int NO_SET = 0; // Valor por default de un int.
	
	public static final int CONST = 1;
	public static final int VAR = 2;
	public static final int ARRAY = 3;
	public static final int PROC = 4;
	public static final int FUNC = 5;
	
	public static final int LAMBDA = 6;
	
	public static final int BOOLEAN = 7;
	
	public static final int INTEGER = 8;
	public static final int NATURAL = 9;
	
	public static final String GLOBAL = "GLOBAL";
	
	private Map<String,Ambiente> listaAmbientes = new HashMap<String,Ambiente>();

	//Singleton
	private static SymbolTable instance = new SymbolTable();
	private SymbolTable() {}
	public static SymbolTable getInstance() { return instance; }
	
	
	public boolean containsAmbiente(String envName) {
		return listaAmbientes.containsKey(envName);
	}
	
	public void addAmbiente(String name, Ambiente env) {
		env.setName(name);
		listaAmbientes.put(name, env);
	}
	
	public Map<String, Ambiente> getListaAmbientes() {
		return listaAmbientes;
	}
	
	public Ambiente getAmbiente(String name) {
		return listaAmbientes.get(name);
	}
	public String getVerboseType(int type) {
	
		switch (type){
		case NO_SET  : return "NO_SET";
		case VOID    : return "VOID";
		case CONST   : return "CONST";
		case VAR     : return "VAR";
		case ARRAY   : return "ARRAY";
		case PROC    : return "PROC";
		case FUNC    : return "FUNC";
		case LAMBDA  : return "LAMBDA";
		case BOOLEAN : return "BOOLEAN";
		case INTEGER : return "INTEGER";
		case NATURAL : return "NATURAL";
		default      : return null;
		}
		
	}
	
	public void cleanAll(){
		listaAmbientes.clear();
	}
	
}
