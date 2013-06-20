package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class SimboloTerminal extends ProduccionC {

	private String simbolo;
	
	public SimboloTerminal(String s){
		this.simbolo = s; 
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	@Override
	public void add(ProduccionI simbolo) {
		System.out.println("ERROR: Un terminal no puede tener producciones hijas.");
	}

	@Override
	public void remove(ProduccionI simbolo) {
		System.out.println("ERROR: Un terminal no puede tener producciones hijas.");
	}
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r = false;
		if(simbolo.equals(sintactic.siguiente.accept(visitor)))
		{
//				System.out.println("CONSUMO TERMINAL");
				sintactic.consumir(lexic);
				r = true;
		}
		return r;
	}
}
