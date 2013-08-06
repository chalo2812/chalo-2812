package ar.edu.caece.pl.asem.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Constante;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asem.model.impl.treeelements.ReturnType;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.treeelements.Variable;

public class Ambiente {	//Ambiente vendría a ser una fila de la tabla de símbolos (idéntico a un procedimiento o funcion
	
	private String name;	//Nombre del ambiente
	private Map<String,Variable> variables = new LinkedHashMap<String,Variable>();
	private Map<String,Constante> constantes = new LinkedHashMap<String,Constante>();
	private Map<String,Arreglo> arreglos = new LinkedHashMap<String,Arreglo>();
	private List<Parametro> parametros = new ArrayList<Parametro>();	//Este es arrayList porque debe ser ordenado y no hasheado
	private List<Variable> temporales = new ArrayList<Variable>();
	
	private ReturnType returnType;
	
	public boolean contains(SimboloGenerico simbolo) {
		
		return (this.arreglos.containsValue(simbolo) ||
				this.constantes.containsValue(simbolo) ||
				this.parametros.contains(simbolo) ||
				this.variables.containsValue(simbolo));
	}
	public boolean containsVar(String name) {
		return this.variables.containsKey(name);
	}
	public boolean containsConst(String name) {
		return this.constantes.containsKey(name);
	}
	public boolean containsArray(String name) {
		return this.arreglos.containsKey(name);
	}
	public boolean containsParam(String name) {
		for(Parametro param : this.parametros) {
			if(param.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void add(SimboloGenerico ac) {
		if(ac instanceof Variable) {
			this.add((Variable)ac);
		} else if(ac instanceof Constante) {
			this.add((Constante)ac);
		} else if(ac instanceof Arreglo) {
			this.add((Arreglo)ac);
		} else if(ac instanceof Parametro) {
			this.add((Parametro)ac);
		}
	}
	
	private void add(Variable var) {
		var.setEnvName(this.name);
		this.variables.put(var.getName(),var);
	}
	private void add(Constante cons) {
		cons.setEnvName(this.name);
		this.constantes.put(cons.getName(),cons);
	}
	private void add(Arreglo array) {
		array.setEnvName(this.name);
		this.arreglos.put(array.getName(),array);
	}
	private void add(Parametro param) {
		param.setEnvName(this.name);
		this.parametros.add(param);
	}
	public Variable getVariable(String name) {
		Variable result = this.variables.get(name);
		if(result == null && !this.name.equals(SymbolTable.GLOBAL))
			result = SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariable(name);
		return result;
	}
	public Constante getConstante(String name) {
		Constante result = this.constantes.get(name);
		if(result == null && !this.name.equals(SymbolTable.GLOBAL))
			result = SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstante(name);
		return result;
	}
	public Arreglo getArreglo(String name) {
		Arreglo result = this.arreglos.get(name);
		if(result == null && !this.name.equals(SymbolTable.GLOBAL))
			result = SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglo(name);
		return result;
	}
	public Parametro getParametro(String name) {
		for(Parametro param : this.parametros) {
			if(param.getName().equals(name)) {
				return param;
			}
		}
		return null;
	}
	public List<Parametro> getParametros(){
		return this.parametros;
	}
	
	
	/**
	 * Retorna un simbolo local o en su defecto el global segun el identificador
	 * @param identificador
	 * @return SimboloGenerico
	 */
	public SimboloGenerico getSimbolo (String identificador) {
		
		SimboloGenerico sg = null;
		
		Ambiente global = SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL);
		
		if(global.containsConst(identificador)) {
			sg = global.getConstante(identificador);
		} else if(global.containsArray(identificador)) {
			sg = global.getArreglo(identificador);
		} else if(global.containsParam(identificador)) {
			sg = global.getParametro(identificador);
		} else if(global.containsVar(identificador)) {
			sg = global.getVariable(identificador);
		}
		
		//Pisar con variables locales
		if(this.containsConst(identificador)) {
			sg = this.getConstante(identificador);
		} else if(this.containsArray(identificador)) {
			sg = this.getArreglo(identificador);
		} else if(this.containsParam(identificador)) {
			sg = this.getParametro(identificador);
		} else if(this.containsVar(identificador)) {
			sg = this.getVariable(identificador);
		}
		return sg;
	}
	
	
	// ASEM-TYPE_CHECK.
	public ArrayList<Integer> getTypes(String name){
		
		ArrayList<Integer> types = new ArrayList<Integer>(); 
		
		types.addAll(getTypeVariables(name));
		types.addAll(getTypeConstantes(name));
		//types.addAll(getTypeArreglos(name));
		types.addAll(getTypeParametros(name));
		
		// Agrego el ambiente global
		Ambiente global = SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL);
		types.addAll(global.getTypeVariables(name));
		types.addAll(global.getTypeConstantes(name));
		
		return types;
	}
	
	private ArrayList<Integer> getTypeParametros(String name) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(Parametro p : this.parametros ) {	
			if (p.getName().equals(name)){
				l.add(p.getType());
			}
		}
		return l;
	}
	
	private ArrayList<Integer> getTypeVariables(String name) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(Variable v : this.variables.values()) {	
			if (v.getName().equals(name)){
				l.add(v.getType());
			}
		}
		return l;
	}
	
	private Collection<Integer> getTypeConstantes(String name) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(Constante c : this.constantes.values()) {
			if (c.getName().equals(name) ){
				l.add(c.getType());
			}
		}	
		return l;
	}
	public int getVariablesSize() {
		return variables.size();
	}
	public int getConstantesSize() {
		return constantes.size();
	}
	public int getArreglosSize() {
		return arreglos.size();
	}
	public int getParametrosSize() {
		return parametros.size();
	}
	public ReturnType getReturnType() {
		return returnType;
	}
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Variable> getVariables() {
		return variables;
	}
	public Map<String, Constante> getConstantes() {
		return constantes;
	}
	public Map<String, Arreglo> getArreglos() {
		return arreglos;
	}
	public List<Variable> getTemporales() {
		return temporales;
	}
	
}
