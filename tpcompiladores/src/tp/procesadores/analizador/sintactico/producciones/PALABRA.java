package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class PALABRA extends ProduccionC {
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
//		System.out.println("PALABRA");
		if ( sintactic.siguiente.getClass() == Palabra.class){ 
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}

}
