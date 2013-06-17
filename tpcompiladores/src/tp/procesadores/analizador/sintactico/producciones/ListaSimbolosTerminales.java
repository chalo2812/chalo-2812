package tp.procesadores.analizador.sintactico.producciones;

import java.util.Arrays;
import java.util.List;
import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class ListaSimbolosTerminales implements ProduccionI{

	private List<String> simbolos; 

	public ListaSimbolosTerminales(String[] c){
		simbolos = Arrays.asList(c);
	}


	@Override
	public void add(ProduccionI simbolo) {
		System.out.println("ERROR: Terminal no puede agregar prducciones hijas");
	}

	@Override
	public void remove(ProduccionI simbolo) {
		System.out.println("ERROR: Terminal no puede tener prducciones hijas");
	}

	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r = false;
		if(simbolos.contains(sintactic.siguiente.accept(visitor)))
		{
				sintactic.consumir(lexic);
				r = true;
		}
		return r;
	}
	
}
