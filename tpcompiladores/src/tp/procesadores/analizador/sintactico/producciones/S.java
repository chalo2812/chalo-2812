package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class S extends ProduccionC {
	
	public S(){
		GLOBALES globales = null; 
		producciones.add(globales);
		SUBRUTINA subrutina = null; 
		producciones.add(subrutina);
	}
	
	//S ->   GLOBALES SUBRUTINA
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("S");
		if( sintactic.siguiente.accept(visitor).equals("globales")){ 
			producciones.set(0, new GLOBALES());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
	
		if ((sintactic.siguiente.accept(visitor).equals("funcion")) || ( sintactic.siguiente.accept(visitor).equals("procedimiento")))
		{
			producciones.set(1, new SUBRUTINA());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera palabra reservada 'funcion' o 'procedimiento'", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r;
	}
	
}
