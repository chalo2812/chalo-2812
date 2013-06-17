package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Cadena;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class CADENA extends ProduccionC {
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
//		System.out.println("CADENA");
		if ( sintactic.siguiente.getClass() == Cadena.class )
		{
			sintactic.consumir(lexic);
		}else 
		{
			merrores.mostrarYSkipearError("Se espera una cadena", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}

}
